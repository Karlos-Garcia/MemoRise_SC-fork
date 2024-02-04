package com.example.memorise.ui.screens.noteScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.memorise.R
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.presentation.ScreenNavigations.Screens
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteEvent
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteViewModel
import com.example.memorise.ui.screens.Topappbar
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuadrantNote(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    Topappbar (
        navController = navController,
        name = "Quadrant Note"
    ) {
        viewModel.onNoteTypeSelected(NoteType.QUADRANT)
        QuadrantTextFields(navController, viewModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuadrantTextFields(
navController: NavController,
viewModel: AddEditNoteViewModel,
modifier: Modifier = Modifier,
) {
    val titleState = viewModel.noteTitle.value
    val keyword1State = viewModel.noteKeyword1.value
    val keyword2State = viewModel.noteKeyword2.value
    val keyword3State = viewModel.noteKeyword3.value
    val keyword4State = viewModel.noteKeyword4.value
    val content1State = viewModel.noteContent1.value
    val content2State = viewModel.noteContent2.value
    val content3State = viewModel.noteContent3.value
    val content4State = viewModel.noteContent4.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
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
            BottomAppBar(
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
        content = {values ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 76.dp,
                        start = 4.dp,
                        bottom = 4.dp,
                        end = 4.dp
                    )
                    .padding(values)
                    .verticalScroll(rememberScrollState())
            ) {
                TextField(
                    label = {Text(text = "Title")},
                    value = titleState.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 2.dp,
                            bottom = 4.dp,
                            end = 2.dp
                        )
                )
                Row(
                    modifier = Modifier
                ) {
                    //top left keyword and content
                    Column() {
                        TextField(
                            singleLine = true,
                            label= {Text(text = "Keyword")},
                            value = keyword1State.text,
                            onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredKeyword1(it))
                            },
                            modifier = Modifier
                                .padding(
                                    bottom = 4.dp,
                                    end = 2.dp
                                )
                                .fillMaxWidth(0.5f)

                        )
                        TextField(
                            label= {Text(text = "Content")},
                            value = content1State.text,
                            onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredContent1(it))
                            },
                            modifier = Modifier
                                .padding(
                                    end = 4.dp,
                                    bottom = 4.dp
                                )
                                .fillMaxWidth(0.5f)
                                .height(340.dp)
//                        .fillMaxWidth(0.5f)
//                        .fillMaxHeight(0.45f)
                        )
                    }
                    //top right keyword and content
                    Column() {
                        TextField(
                            singleLine = true,
                            label= {Text(text = "Keyword")},
                            value = keyword2State.text,
                            onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredKeyword2(it))
                            },
                            modifier = Modifier
                                .padding(
                                    bottom = 4.dp
                                )
                                .fillMaxWidth(1f)

                        )
                        TextField(
                            label= {Text(text = "Content")},
                            value = content2State.text,
                            onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredContent2(it))
                            },
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(340.dp)
                        )
                    }
                }
                Row {
                    Column() {
                        TextField(
                            singleLine = true,
                            label= {Text(text = "Keyword")},
                            value = keyword3State.text,
                            onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredKeyword3(it))
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(
                                    bottom = 4.dp,
                                    end = 2.dp
                                )
                        )
                        TextField(
                            label= {Text(text = "Content")},
                            value = content3State.text,
                            onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredContent3(it))
                            },
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                )
                                .fillMaxWidth(0.5f)
                                .height(340.dp)
                        )
                    }
                    Column() {
                        TextField(
                            singleLine = true,
                            label= {Text(text = "Keyword")},
                            value = keyword4State.text,
                            onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredKeyword4(it))
                            },
                            modifier = Modifier
                                .padding(
                                    bottom = 4.dp
                                )
                                .fillMaxWidth(1f)
                        )
                        TextField(
                            label= {Text(text = "Content")},
                            value = content4State.text,
                            onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredContent4(it))
                            },
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(340.dp)
                        )
                    }
                }
            }
        }
    )
}
