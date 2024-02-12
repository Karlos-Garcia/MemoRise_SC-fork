package com.example.memorise.ui.screens.noteScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun OutlineNote(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    val resources = LocalContext.current.resources
    var showInformationDialog = remember { mutableStateOf(false) }

    Topappbar (
        navController = navController,
        name = "Outline Note",
        showInformationIcon = true,
        showInformationDialog = showInformationDialog.value,
        onInformationClick = {showInformationDialog.value = true},
        dialogTitle = "Outline note information",
        dialogText = resources.getString(R.string.Outline_note_information),
        onDismiss = {showInformationDialog.value = false},
        onBackClicked = {
            navController.navigateUp()
        }
    ) {
        viewModel.onNoteTypeSelected(NoteType.OUTLINE)
        outlineNote(navController, viewModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun outlineNote(
    navController: NavController,
    viewModel: AddEditNoteViewModel,
    modifier: Modifier = Modifier
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
                        .padding(
                            top = 72.dp
                        )
                        .padding(paddingValues)
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
                                start = 8.dp,
                                bottom = 8.dp,
                                end = 8.dp
                            )
                    )
                    TextField(
                        label = {Text(text = "Keyword")},
                        value = keyword1State.text,
                        onValueChange = {
                            viewModel.onEvent(AddEditNoteEvent.EnteredKeyword1(it))
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 8.dp,
                                bottom = 8.dp,
                                end = 8.dp
                            )
                    )
                    TextField(
                        label = {Text(text = "Content")},
                        value = content1State.text,
                        onValueChange = {
                            viewModel.onEvent(AddEditNoteEvent.EnteredContent1(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp)
                            .padding(
                                start = 8.dp,
                                bottom = 8.dp,
                                end = 8.dp
                            )
                    )
                    TextField(
                        label = {Text(text = "Keyword")},
                        value = keyword2State.text,
                        onValueChange = {
                            viewModel.onEvent(AddEditNoteEvent.EnteredKeyword2(it))
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 8.dp,
                                bottom = 8.dp,
                                end = 8.dp
                            )
                    )

                    TextField(
                        label = {Text(text = "Content")},
                        value = content2State.text,
                        onValueChange = {
                            viewModel.onEvent(AddEditNoteEvent.EnteredContent2(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp)
                            .padding(
                                start = 8.dp,
                                bottom = 8.dp,
                                end = 8.dp
                            )
                    )
                }
            }
    )
}
