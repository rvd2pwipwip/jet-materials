package com.raywenderlich.android.jetnotes.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetnotes.domain.model.NoteModel
import com.raywenderlich.android.jetnotes.routing.Screen
import com.raywenderlich.android.jetnotes.ui.components.AppDrawer
import com.raywenderlich.android.jetnotes.ui.components.Note
import com.raywenderlich.android.jetnotes.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterialApi
fun NotesScreen(viewModel: MainViewModel) {

  val notes: List<NoteModel> by viewModel
    .notesNotInTrash
    .observeAsState(listOf())

  val scaffoldState: ScaffoldState = rememberScaffoldState()

  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "JetNotes",
            color = MaterialTheme.colors.onPrimary
          )
        },
        navigationIcon = {
          IconButton(onClick = {
            coroutineScope.launch { scaffoldState.drawerState.open() }
          }) {
            Icon(
              imageVector = Icons.Filled.List,
              contentDescription = "Drawer Button"
            )
          }
        }
      )
    },
    scaffoldState = scaffoldState, // here - Scaffold state
    drawerContent = { // here - Drawer UI
      AppDrawer(
        currentScreen = Screen.Notes,
        closeDrawerAction = {
          coroutineScope.launch { scaffoldState.drawerState.close() } // here - Drawer close
        }
      )
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
      FloatingActionButton(
        onClick = { viewModel.onCreateNewNoteClick() },
        contentColor = MaterialTheme.colors.background,
        content = {
          Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Note Button"
          )
        }
      )
    },
    content = {
      if (notes.isNotEmpty()) {
        NotesList(
          notes = notes,
          onNoteCheckedChange = {
            viewModel.onNoteCheckedChange(it)
          },
          onNoteClick = { viewModel.onNoteClick(it) }
        )
      }
    }
  )
}

@Composable
@ExperimentalMaterialApi
private fun NotesList(
  notes: List<NoteModel>,
  onNoteCheckedChange: (NoteModel) -> Unit,
  onNoteClick: (NoteModel) -> Unit
) {
  LazyColumn {
    items(count = notes.size) { noteIndex ->
      val note = notes[noteIndex]
      val bottomPadding = if (notes.last() == note) 72.dp else 0.dp
      Note(
        modifier = Modifier.padding(bottom = bottomPadding),
        note = note,
        onNoteClick = onNoteClick,
        onNoteCheckedChange = onNoteCheckedChange
      )
    }
  }
}

@Preview
@Composable
@ExperimentalMaterialApi
private fun NotesListPreview() {
  NotesList(
    notes = listOf(
      NoteModel(1, "Note 1", "Content 1", null),
      NoteModel(2, "Note 2", "Content 2", false),
      NoteModel(3, "Note 3", "Content 3", true)
    ),
    onNoteCheckedChange = {},
    onNoteClick = {}
  )
}