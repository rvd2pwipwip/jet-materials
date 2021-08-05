package com.raywenderlich.android.jetnotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.res.vectorResource
import com.raywenderlich.android.jetnotes.R
import com.raywenderlich.android.jetnotes.domain.model.NoteModel
import com.raywenderlich.android.jetnotes.routing.Screen
import com.raywenderlich.android.jetnotes.ui.components.AppDrawer
import com.raywenderlich.android.jetnotes.ui.components.Note
import com.raywenderlich.android.jetnotes.viewmodel.MainViewModel

private const val NO_DIALOG = 1
private const val RESTORE_NOTES_DIALOG = 2
private const val PERMANENTLY_DELETE_DIALOG = 3

@ExperimentalMaterialApi
@Composable
fun TrashScreen(viewModel: MainViewModel) {

  val notesInThrash: List<NoteModel> by viewModel.notesInTrash
    .observeAsState(listOf())

  val selectedNotes: List<NoteModel> by viewModel.selectedNotes
    .observeAsState(listOf())

  var dialog: Int by savedInstanceState { NO_DIALOG }

  val scaffoldState: ScaffoldState = rememberScaffoldState()

  Scaffold(
    topBar = {
      val areActionsVisible = selectedNotes.isNotEmpty()
      TrashTopAppBar(
        onNavigationIconClick = { scaffoldState.drawerState.open() },
        onRestoreNotesClick = { dialog = RESTORE_NOTES_DIALOG },
        onDeleteNotesClick = { dialog = PERMANENTLY_DELETE_DIALOG },
        areActionsVisible = areActionsVisible
      )
    },
    scaffoldState = scaffoldState,
    drawerContent = {
      AppDrawer(
        currentScreen = Screen.Trash,
        closeDrawerAction = { scaffoldState.drawerState.close() }
      )
    },
    bodyContent = {
      Content(
        notes = notesInThrash,
        onNoteClick = { viewModel.onNoteSelected(it) },
        selectedNotes = selectedNotes
      )

      if (dialog != NO_DIALOG) {
        val confirmAction: () -> Unit = when (dialog) {
          RESTORE_NOTES_DIALOG -> {
            {
              viewModel.restoreNotes(selectedNotes)
              dialog = NO_DIALOG
            }
          }
          PERMANENTLY_DELETE_DIALOG -> {
            {
              viewModel.permanentlyDeleteNotes(selectedNotes)
              dialog = NO_DIALOG
            }
          }
          else -> {
            {
              dialog = NO_DIALOG
            }
          }
        }

        AlertDialog(
          onDismissRequest = {
            dialog = NO_DIALOG
          },
          title = { Text(mapDialogTitle(dialog)) },
          text = { Text(mapDialogText(dialog)) },
          confirmButton = {
            TextButton(onClick = confirmAction) {
              Text("Confirm")
            }
          },
          dismissButton = {
            TextButton(onClick = { dialog = NO_DIALOG }) {
              Text("Dismiss")
            }
          }
        )
      }
    }
  )
}

@Composable
private fun TrashTopAppBar(
  onNavigationIconClick: () -> Unit,
  onRestoreNotesClick: () -> Unit,
  onDeleteNotesClick: () -> Unit,
  areActionsVisible: Boolean
) {
  TopAppBar(
    title = { Text(text = "Trash", color = MaterialTheme.colors.onPrimary) },
    navigationIcon = {
      IconButton(onClick = onNavigationIconClick) {
        Icon(Icons.Filled.List)
      }
    },
    actions = {
      if (areActionsVisible) {
        IconButton(onClick = onRestoreNotesClick) {
          Icon(
            imageVector = vectorResource(id = R.drawable.ic_baseline_restore_from_trash_24),
            tint = MaterialTheme.colors.onPrimary
          )
        }
        IconButton(onClick = onDeleteNotesClick) {
          Icon(
            imageVector = vectorResource(id = R.drawable.ic_baseline_delete_forever_24),
            tint = MaterialTheme.colors.onPrimary
          )
        }
      }
    }
  )
}

@ExperimentalMaterialApi
@Composable
private fun Content(
  notes: List<NoteModel>,
  onNoteClick: (NoteModel) -> Unit,
  selectedNotes: List<NoteModel>,
) {
  val tabs = listOf("REGULAR", "CHECKABLE")

  // Init state for selected tab
  var selectedTab by remember { mutableStateOf(0) }

  Column {
    TabRow(selectedTabIndex = selectedTab) {
      tabs.forEachIndexed { index, title ->
        Tab(
          text = { Text(title) },
          selected = selectedTab == index,
          onClick = { selectedTab = index }
        )
      }
    }

    val filteredNotes = when (selectedTab) {
      0 -> {
        notes.filter { it.isCheckedOff == null }
      }
      1 -> {
        notes.filter { it.isCheckedOff != null }
      }
      else -> throw IllegalStateException("Tab not supported - index: $selectedTab")
    }

    LazyColumn {
      items(
        items = filteredNotes,
        itemContent = { note ->
          val isNoteSelected = selectedNotes.contains(note)
          Note(
            note = note,
            onNoteClick = onNoteClick,
//            isSelected = isNoteSelected
          )
        }
      )
    }
  }
}

private fun mapDialogTitle(dialog: Int): String = when (dialog) {
  RESTORE_NOTES_DIALOG -> "Restore notes"
  PERMANENTLY_DELETE_DIALOG -> "Delete notes forever"
  else -> throw RuntimeException("Dialog not supported: $dialog")
}

private fun mapDialogText(dialog: Int): String = when (dialog) {
  RESTORE_NOTES_DIALOG -> "Are you sure you want to restore selected notes?"
  PERMANENTLY_DELETE_DIALOG -> "Are you sure you want to delete selected notes permanently?"
  else -> throw RuntimeException("Dialog not supported: $dialog")
}