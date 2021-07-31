package com.raywenderlich.android.jetnotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.raywenderlich.android.jetnotes.R
import com.raywenderlich.android.jetnotes.domain.model.NoteModel
import com.raywenderlich.android.jetnotes.ui.components.Note
import com.raywenderlich.android.jetnotes.ui.components.TopAppBar
import com.raywenderlich.android.jetnotes.viewmodel.MainViewModel

@Composable
fun NotesScreen(viewModel: MainViewModel) {

  val notes: List<NoteModel> by viewModel.notesNotInTrash.observeAsState(listOf()) //pass listOf() to avoid List<R>?

  Column {
    TopAppBar(
      title = stringResource(id = R.string.app_name),
      icon = Icons.Filled.List,
      onIconClick = {}
    )
    NotesList(
      notes = notes,
      onNoteCheckedChange = { viewModel.onNoteCheckedChange(it) },
      onNoteClick = { viewModel.onNoteClick(it) }
    )
  }
}

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