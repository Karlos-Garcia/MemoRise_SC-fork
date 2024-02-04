package com.example.memorise.ui.screens.noteScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memorise.R
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.presentation.ScreenNavigations.Screens
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteEvent
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteViewModel
import com.example.memorise.ui.screens.Topappbar
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun basicNote(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    ) {
    Topappbar (
        navController = navController,
        name = "Basic Note",
    ) {
        viewModel.onNoteTypeSelected(NoteType.BASIC)
        basicTextFields(navController, viewModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun basicTextFields(
    navController: NavController ,
    viewModel: AddEditNoteViewModel,
    modifier: Modifier = Modifier,
) {
    val titleState = viewModel.noteTitle.value
    val content1State = viewModel.noteContent1.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigate(Screens.MainScreen.route)
                }

            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        bottomBar = {
            androidx.compose.material3.BottomAppBar(
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource (id = R.drawable.format_bold_fill0_wght400_grad0_opsz24),
                            contentDescription = "Bold")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource (id = R.drawable.format_italic_fill0_wght400_grad0_opsz24),
                            contentDescription = "Italic")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource (id = R.drawable.format_underlined_fill0_wght400_grad0_opsz24),
                            contentDescription = "Underline")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save Note",
                            modifier = Modifier
                        )
                    }
                }
            )
        },
        content = { values ->
            Column(
            modifier = Modifier
                .padding(
                    top = 76.dp
                )
                .padding(values)
                .fillMaxWidth()
        ) {
            TextField(
                label = { Text(text = "Title") },
                value = titleState.text,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                singleLine = true,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                    )
                    .clip(RoundedCornerShape(12.dp))
            )
            TextField(
                label = { Text(text = "Content") },
                value = content1State.text,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent1(it))
                },
                textStyle = TextStyle(
                    fontWeight = if (viewModel.isBold.value) FontWeight.Bold else null,
                    fontStyle = if (viewModel.isItalic.value) FontStyle.Italic else null,
                    textDecoration = if (viewModel.isUnderlined.value) TextDecoration.LineThrough else null
                ),
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    )
}