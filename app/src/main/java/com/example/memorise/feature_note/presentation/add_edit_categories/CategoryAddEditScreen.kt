package com.example.memorise.feature_note.presentation.add_edit_categories

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memorise.feature_note.presentation.add_edit_categories.components.AddEditCategoryEvent
import com.example.memorise.feature_note.presentation.add_edit_categories.components.AddEditCategoryViewModel
//import com.example.memorise.feature_note.presentation.TextFormatting.TextFormattingBar
import com.example.memorise.ui.screens.Topappbar
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditCategoryScreen(
    navController: NavController,
    viewModel: AddEditCategoryViewModel = hiltViewModel(),
) {
    Topappbar (
        navController = navController,
        name = "Category"
    ) {
        categoryTextFields(navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun categoryTextFields(
    navController: NavController ,
    viewModel: AddEditCategoryViewModel,
    modifier: Modifier = Modifier,
) {
    val titleState = viewModel.categoryTitle.value

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditCategoryViewModel.UiCategoryEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditCategoryViewModel.UiCategoryEvent.SaveCategory -> {
                    navController.navigateUp()
                }

            }
        }
    }
    Column(
        modifier = Modifier
            .padding(
                top = 76.dp,
            )
            .fillMaxWidth()
    ) {
        TextField(
            label = { Text(text = "Title") },
            value = titleState.text,
            onValueChange = {
                viewModel.onEvent(AddEditCategoryEvent.EnteredTitle(it))
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            IconButton(
                onClick = {
                    viewModel.onEvent(AddEditCategoryEvent.SaveCategory)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save Note",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
    }
}