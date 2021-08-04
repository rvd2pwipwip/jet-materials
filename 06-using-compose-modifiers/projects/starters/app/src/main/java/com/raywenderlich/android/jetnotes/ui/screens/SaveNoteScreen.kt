package com.raywenderlich.android.jetnotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetnotes.domain.model.ColorModel
import com.raywenderlich.android.jetnotes.domain.model.NEW_NOTE_ID
import com.raywenderlich.android.jetnotes.domain.model.NoteModel
import com.raywenderlich.android.jetnotes.routing.JetNotesRouter
import com.raywenderlich.android.jetnotes.routing.Screen
import com.raywenderlich.android.jetnotes.theme.JetNotesTheme
import com.raywenderlich.android.jetnotes.ui.components.NoteColor
import com.raywenderlich.android.jetnotes.util.fromHex
import com.raywenderlich.android.jetnotes.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun SaveNoteScreen(viewModel: MainViewModel) {

  val noteEntry: NoteModel by viewModel.noteEntry
    .observeAsState(
      initial = NoteModel()
    )

  val colors: List<ColorModel> by viewModel.colors
    .observeAsState(
      initial = listOf()
    )

  val bottomDrawerState: BottomDrawerState =
    rememberBottomDrawerState(BottomDrawerValue.Closed)

  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    topBar = {
      val isEditingMode: Boolean = noteEntry.id != NEW_NOTE_ID
      SaveNoteTopAppBar(
        isEditingMode = isEditingMode,
        onBackClick = { JetNotesRouter.navigateTo(Screen.Notes) },
        onSaveNoteClick = { viewModel.saveNote(noteEntry) },
        onOpenColorPickerClick = {
          coroutineScope.launch { bottomDrawerState.open() }
        },
        onDeleteNoteClick = { viewModel.moveNoteToTrash(noteEntry) }
      )
    },
    bodyContent = {
      SaveNoteContent(
        note = noteEntry,
        onNoteChange = { updateNoteEntry ->
          viewModel.onNoteEntryChange(updateNoteEntry)
        }
      )
    }
  )
}

@Composable
private fun SaveNoteContent(
  note: NoteModel,
  onNoteChange: (NoteModel) -> Unit
) {
  Column(Modifier.fillMaxSize()) {
    ContentTextField(
      label = "Title",
      text = note.title,
      onTextChange = {
        onNoteChange.invoke(note.copy(title = it))
      }
    )

    ContentTextField(
      modifier = Modifier
        .heightIn(max = 240.dp)
        .padding(top = 16.dp),
      label = "Body",
      text = note.content,
      onTextChange = {
        onNoteChange.invoke(note.copy(content = it))
      }
    )

    val canBeCheckedOff: Boolean = note.isCheckedOff != null

    NoteCheckOption(
      isChecked = canBeCheckedOff,
      onCheckedChange = { canBeCheckedOffNewValue ->
        val isCheckedOff: Boolean? = if (canBeCheckedOffNewValue) false else null
        onNoteChange.invoke(note.copy(isCheckedOff = isCheckedOff))
      }
    )

    PickedColor(color = note.color)
  }
}

@Composable
private fun SaveNoteTopAppBar(
  isEditingMode: Boolean,
  onBackClick: () -> Unit,
  onSaveNoteClick: () -> Unit,
  onOpenColorPickerClick: () -> Unit,
  onDeleteNoteClick: () -> Unit,
) {
  TopAppBar(
    title = {
      Text(
        text = "Save Note",
        color = MaterialTheme.colors.onPrimary
      )
    },
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          tint = MaterialTheme.colors.onPrimary
        )
      }
    },
    actions = {
      // Save note action icon
      IconButton(onClick = onSaveNoteClick) {
        Icon(
          imageVector = Icons.Default.Save,
          tint = MaterialTheme.colors.onPrimary
        )
      }
      // Open color picker action icon
      IconButton(onClick = onOpenColorPickerClick) {
        Icon(
          imageVector = Icons.Default.Palette,
          tint = MaterialTheme.colors.onPrimary
        )
      }
      // Delete action icon (show only in editing mode)
      if (isEditingMode) {
        IconButton(onClick = onDeleteNoteClick) {
          Icon(
            imageVector = Icons.Default.Delete,
            tint = MaterialTheme.colors.onPrimary
          )
        }
      }
    }
  )
}

@Composable
private fun ContentTextField(
  modifier: Modifier = Modifier,
  label: String,
  text: String,
  onTextChange: (String) -> Unit
) {
  TextField(
    value = text,
    onValueChange = onTextChange,
    label = { Text(text = label) },
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
    backgroundColor = MaterialTheme.colors.surface
  )
}

@Composable
private fun NoteCheckOption(
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    Modifier
      .padding(8.dp)
      .padding(top = 16.dp)
  ) {
    Text(
      text = "Can note be checked off?",
      modifier = Modifier.weight(1f)
    )
    Switch(
      checked = isChecked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colors.primary,
        checkedTrackColor = MaterialTheme.colors.primary
      ),
      modifier = Modifier.padding(start = 8.dp)
    )
  }
}

@Composable
private fun PickedColor(
  color: ColorModel
) {
  Row(
    Modifier
      .padding(8.dp)
      .padding(top = 16.dp)
  ) {
    Text(
      text = "Picked color",
      modifier = Modifier
        .weight(1f)
        .align(Alignment.CenterVertically)
    )
    NoteColor(
      color = Color.fromHex(color.hex),
      size = 40.dp,
      border = 1.dp,
      modifier = Modifier.padding(4.dp)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun SaveNoteTopAppBarPreview() {
  SaveNoteTopAppBar(
    isEditingMode = true,
    onBackClick = {},
    onSaveNoteClick = {},
    onOpenColorPickerClick = {},
    onDeleteNoteClick = {}
  )
}

@Preview(showBackground = true)
@Composable
fun SaveNoteContentPreview() {
  SaveNoteContent(
    note = NoteModel(title = "Title", content = "content"),
    onNoteChange = {}
  )
}

@Preview(showBackground = true)
@Composable
fun PickedColorPreview() {
  PickedColor(ColorModel.DEFAULT)
}

@Preview(showBackground = true)
@Composable
fun NoteCheckOptionPreview() {
  JetNotesTheme {
    NoteCheckOption(false) {}
  }
}

@Preview(showBackground = true)
@Composable
fun ContentTextFieldPreview() {
  ContentTextField(
    label = "Title",
    text = "",
    onTextChange = {}
  )
}
