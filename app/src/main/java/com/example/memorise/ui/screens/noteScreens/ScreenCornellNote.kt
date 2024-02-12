package com.example.memorise.ui.screens.noteScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.memorise.R
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.presentation.ScreenNavigations.Screens
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteEvent
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteViewModel
import com.example.memorise.ui.screens.Bottomappbar
import com.example.memorise.ui.screens.Topappbar
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CornellNote(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val resources = LocalContext.current.resources
    var showInformationDialog = remember { mutableStateOf(false) }

    Topappbar (
        navController = navController,
        name = "Cornell Note",
        showInformationIcon = true,
        showInformationDialog = showInformationDialog.value,
        onInformationClick = {showInformationDialog.value = true},
        dialogTitle = "Cornell note information",
        dialogText = resources.getString(R.string.Cornell_note_information),
        onDismiss = {showInformationDialog.value = false},
        onBackClicked = {
            navController.navigateUp()
        }
    ) {
        viewModel.onNoteTypeSelected(NoteType.CORNELL)
        cornellTextFields(navController, viewModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cornellTextFields(
    navController: NavController,
    viewModel: AddEditNoteViewModel,
    modifier: Modifier = Modifier,
) {
    val folder = rememberFoldersState(viewModel)
    val selectedFolder by viewModel.selectedFolder.collectAsState()

    val categories = rememberCategoriesState(viewModel)
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val titleState = viewModel.noteTitle.value
    val keyword1State = viewModel.noteKeyword1.value
    val keyword2State = viewModel.noteKeyword2.value
    val content1State = viewModel.noteContent1.value
    val content2State = viewModel.noteContent2.value
    val summaryState = viewModel.noteSummary.value

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
                    val startDestination = navController.graph.findStartDestination()?.route ?: ""
                    navController.popBackStack(startDestination, inclusive = true)
                    navController.navigate(Screens.MainScreen.route)
                }
            }
        }
    }

    val labelStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 8.sp,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colorScheme.onSurface
    )

    Bottomappbar(
        navController = navController,
        showFolderDropdown = true,
        folders = folder.value,
        selectedFolder = selectedFolder,
        onFolderSelected = { folder ->
            viewModel.onFolderSelected(folder)
        },
        showCategoryDropdown = true,
        categories = categories.value,
        selectedCategory = selectedCategory,
        onCategorySelected = { category ->
            viewModel.onCategorySelected(category)
        },
        content = {
            paddingValues ->
            Column(
                modifier = Modifier
                    .padding(top = 72.dp)
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                TextField(
                    singleLine = true,
                    label = {Text(text = "Title", style = labelStyle)},
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp
                        ),
                    value = titleState.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                    }
                )
                Row {
                    TextField(
                        label = {Text(text = "Keywords", style = labelStyle)},
                        modifier = modifier
                            .fillMaxWidth(0.25f)
                            .fillMaxHeight(0.25f)
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp,
                            ),
                        value = keyword1State.text,
                        onValueChange = {
                            viewModel.onEvent(AddEditNoteEvent.EnteredKeyword1(it))
                        }
                    )
                    TextField(
                        label = {Text(text = "Content", style = labelStyle)},
                        modifier = modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(0.25f)
                            .padding(
                                end = 8.dp,
                                bottom = 8.dp
                            ),
                        value = content1State.text,
                        onValueChange = {
                            viewModel.onEvent(AddEditNoteEvent.EnteredContent1(it))
                        }
                    )
                }
                Row {
                    TextField(
                        label = {Text(text = "Keywords", style = labelStyle)},
                        modifier = modifier
                            .fillMaxWidth(0.25f)
                            .fillMaxHeight(0.35f)
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp,
                            ),
                        value = keyword2State.text,
                        onValueChange = {
                            viewModel.onEvent(AddEditNoteEvent.EnteredKeyword2(it))
                        }
                    )
                    TextField(
                        label = {Text(text = "Content", style = labelStyle)},
                        modifier = modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(0.35f)
                            .padding(
                                end = 8.dp,
                                bottom = 8.dp,
                            ),
                        value = content2State.text,
                        onValueChange = {
                            viewModel.onEvent(AddEditNoteEvent.EnteredContent2(it))
                        }
                    )
                }
                TextField(
                    label = {Text(text = "Summary", style = labelStyle)},
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 8.dp,
                            bottom = 8.dp,
                            end = 8.dp,
                        ),
                    value = summaryState.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredSummary(it))
                    }
                )
            }
        }
    )
}
