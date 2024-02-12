package com.example.memorise.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memorise.R
import com.example.memorise.feature_note.domain.model.Category
import com.example.memorise.feature_note.domain.model.Folder
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteEvent
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteViewModel
import com.example.memorise.feature_note.presentation.add_edit_notes.components.CategoryDropdown
import com.example.memorise.feature_note.presentation.add_edit_notes.components.FolderDropdown
import com.example.memorise.ui.screens.noteScreens.basicNote
import com.example.memorise.ui.screens.noteScreens.basicTextFields

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bottomappbar(
    navController: NavController,
    showFolderDropdown: Boolean = false,
    folders: List<Folder> = emptyList(),
    selectedFolder: Folder? = null,
    onFolderSelected: (Folder?) -> Unit = {},
    showCategoryDropdown: Boolean = false,
    categories: List<Category> = emptyList(),
    selectedCategory: Category? = null,
    onCategorySelected: (Category?) -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier,
            bottomBar = {
                androidx.compose.material3.BottomAppBar(
                    actions = {
                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = "Folder"
                            )
                            FolderDropdown(
                                folders = folders,
                                selectedFolder = selectedFolder,
                                onFolderSelected = onFolderSelected
                            )
                        }
                        Spacer(
                            modifier = Modifier.padding(8.dp)
                        )
                        Column() {
                            Text(
                                text = "Category"
                            )
                            CategoryDropdown(
                                categories = categories,
                                selectedCategory = selectedCategory,
                                onCategorySelected = onCategorySelected
                            )
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
            content = content
        )
    }
}