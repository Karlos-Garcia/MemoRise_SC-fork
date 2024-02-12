package com.example.memorise.ui.screens.noteScreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memorise.R
import com.example.memorise.feature_note.domain.model.Category
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.presentation.ScreenNavigations.Screens
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteEvent
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteViewModel
import com.example.memorise.ui.screens.Topappbar
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.memorise.feature_note.domain.model.Folder
import com.example.memorise.ui.screens.Bottomappbar

@Composable
fun rememberCategoriesState(viewModel: AddEditNoteViewModel): State<List<Category>> {
    return viewModel.categories.collectAsState(initial = emptyList())
}

@Composable
fun rememberFoldersState(viewModel: AddEditNoteViewModel): State<List<Folder>> {
    return viewModel.folders.collectAsState(initial = emptyList())
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun basicNote(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    ) {
    val scaffoldState = rememberScaffoldState()
    val resources = LocalContext.current.resources
    var showInformationDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Topappbar (
                navController = navController,
                name = "Basic Note",
                showInformationIcon = true,
                showInformationDialog = showInformationDialog.value,
                onInformationClick = {showInformationDialog.value = true},
                dialogTitle = "Basic note information",
                dialogText = resources.getString(R.string.Basic_note_information),
                onDismiss = {showInformationDialog.value = false},
                onBackClicked = {
                    navController.navigateUp()
                }
            ) {
                viewModel.onNoteTypeSelected(NoteType.BASIC)
                basicTextFields(navController, viewModel, scaffoldState)
            }
        },
        content = {
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun basicTextFields(
    navController: NavController ,
    viewModel: AddEditNoteViewModel,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    val folder = rememberFoldersState(viewModel)
    val selectedFolder by viewModel.selectedFolder.collectAsState()

    val categories = rememberCategoriesState(viewModel)
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val titleState = viewModel.noteTitle.value
    val content1State = viewModel.noteContent1.value
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.d("basicTextFields", "Received event: ${event}")
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    Log.d("basicTextFields", "Showing snackbar: ${event.message}")
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
                    .padding(top = 72.dp)
                    .padding(paddingValues)
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