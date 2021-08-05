package com.raywenderlich.android.jetnotes.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.raywenderlich.android.jetnotes.R
import com.raywenderlich.android.jetnotes.domain.model.NoteModel
import com.raywenderlich.android.jetnotes.routing.Screen
import com.raywenderlich.android.jetnotes.ui.components.AppDrawer
import com.raywenderlich.android.jetnotes.ui.components.Note
//import com.raywenderlich.android.jetnotes.ui.components.TopAppBar
//import androidx.compose.material.TopAppBar
import com.raywenderlich.android.jetnotes.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun NotesScreen(viewModel: MainViewModel) {
  // Observing notes state from MainViewModel
  val notes: List<NoteModel> by viewModel.notesNotInTrash.observeAsState(listOf()) //pass listOf() to avoid List<R>?

  // Drawer state
  val scaffoldState: ScaffoldState = rememberScaffoldState()

  // Coroutine scope used for opening/closing the drawer
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colors.onPrimary
          )
        },
        navigationIcon = {
          IconButton(onClick = {
            coroutineScope.launch { scaffoldState.drawerState.open() }
          }) {
            Icon(
              imageVector = Icons.Filled.List,
            )
          }
        }
      )
    },
//    topBar = {
//      TopAppBar(
//        title = stringResource(id = R.string.app_name),
//        icon = Icons.Filled.List,
//        onIconClick = {
//          scaffoldState.drawerState.open()
//        }
//      )
//    },
    drawerContent = {
      AppDrawer(
        currentScreen = Screen.Notes,
        closeDrawerAction = {
          coroutineScope.launch {
            scaffoldState.drawerState.close()
          }
        }
      )
    },
    bodyContent = {
      if (notes.isNotEmpty()) {
        NotesList(
          notes = notes,
          onNoteCheckedChange = { viewModel.onNoteCheckedChange(it) },
          onNoteClick = { viewModel.onNoteClick(it) }
        )
      }
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
      FloatingActionButton(
        onClick = { viewModel.onCreateNewNoteClick() },
        backgroundColor = colorResource(id = R.color.colorAccent),
        contentColor = colorResource(id = R.color.white),
        content = {
          Icon(
            imageVector = Icons.Filled.Add,
          )
        }
      )
    }
  )
}


@ExperimentalMaterialApi
@Composable
private fun NotesList(
  notes: List<NoteModel>,
  onNoteCheckedChange: (NoteModel) -> Unit,
  onNoteClick: (NoteModel) -> Unit,
) {
  LazyColumn {
    items(items = notes) { note ->
      Note(
        note = note,
        onNoteClick = onNoteClick,
        onNoteCheckedChange = onNoteCheckedChange
      )
    }
  }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun NotesListPreview() {
  NotesList(
    notes = listOf(
      NoteModel(1, "Note 1", "Content 1", null),
      NoteModel(2, "Note 2", "Content 2", false),
      NoteModel(3, "Note 3", "Content 3", true)
    ),
    onNoteCheckedChange = {},
    onNoteClick = {})
}