package com.example.memorise.ui.screens.noteScreens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
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
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.presentation.ScreenNavigations.Screens
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteEvent
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteViewModel
import com.example.memorise.ui.screens.Topappbar
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.material.rememberCoilPainter




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ImageNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    Log.d("ImageNoteScreen", "Selected Image URI: ${viewModel.selectedImageUri.value}")

    Topappbar (
        navController = navController,
        name = "Image Note",
    ) {
        viewModel.onNoteTypeSelected(NoteType.IMAGE)
        ImageEditFields(navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageEditFields(
    navController: NavController,
    viewModel: AddEditNoteViewModel,
    modifier: Modifier = Modifier,
) {
    val titleState = viewModel.noteTitle.value
    val content1State = viewModel.noteContent1.value
    val selectedImageUri = viewModel.selectedImageUri.value

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> uri?.let {viewModel.onEvent(AddEditNoteEvent.ImageSelected(it))} }
    )

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
    Column(
        modifier = Modifier
            .padding(
                top = 76.dp,
            )
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                singlePhotoPickerLauncher.launch("image/*")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text(text = "Select Image")
        }
        if (selectedImageUri != null) {
            Image(
                painter = rememberCoilPainter(request = selectedImageUri),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
        TextField(
            label = { Text(text = "Title") },
            value = titleState.text,
            onValueChange = {
                viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
            },
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconButton(
            onClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
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