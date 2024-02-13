package com.example.memorise.ui.screens.noteScreens
//notes below for the used functionality

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteEvent
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteViewModel
import com.example.memorise.ui.screens.Topappbar
import kotlinx.coroutines.flow.collectLatest
import android.graphics.BitmapFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.memorise.R
import com.example.memorise.feature_note.presentation.add_edit_notes.components.DisplayImage
import com.example.memorise.ui.screens.Bottomappbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ImageNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    val resources = LocalContext.current.resources
    var showInformationDialog = remember { mutableStateOf(false) }

    Topappbar (
        navController = navController,
        name = "Image Note",
        showInformationIcon = true,
        showInformationDialog = showInformationDialog.value,
        onInformationClick = {showInformationDialog.value = true},
        dialogTitle = "Image note information",
        dialogText = resources.getString(R.string.Image_note_information),
        onDismiss = {showInformationDialog.value = false},
        onBackClicked = {

            navController.navigateUp()
        }
    ) {
        viewModel.onNoteTypeSelected(NoteType.IMAGE)
        ImageEditFields(navController, viewModel, viewModel.decodedImageBytes.value)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageEditFields(
    navController: NavController,
    viewModel: AddEditNoteViewModel,
    decodedImageBytes: ByteArray?,
    modifier: Modifier = Modifier,
) {
    val folder = rememberFoldersState(viewModel)
    val selectedFolder by viewModel.selectedFolder.collectAsState()

    val categories = rememberCategoriesState(viewModel)
    val selectedCategory by viewModel.selectedCategory.collectAsState()

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
                    val startDestination = navController.graph.findStartDestination()?.route ?: ""
                    navController.navigateUp()
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
                        top = 76.dp,
                    )
                    .padding(paddingValues)
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

                val imageBitmapState = remember { mutableStateOf<Bitmap?>(null) }

                LaunchedEffect(key1 = decodedImageBytes) {
                    if (decodedImageBytes != null) {
                        val imageBitmap = viewModel.viewModelScope.async(Dispatchers.IO) {
                            BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
                        }.await()
                        imageBitmapState.value = imageBitmap
                    }
                }

                DisplayImage(imageBitmapState.value)

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

//*************************notes
//AsyncImage: You're using AsyncImage from Coil Accompanist to display the decoded image bitmap within the DisplayImage composable.
//bitmap factory is from coil-accompanist library
//BitmapFactory: While Coil handles downloading and decoding images from various sources, the code uses BitmapFactory.decodeByteArray to decode the already acquired decodedImageBytes data.
//LaunchedEffect: The LaunchedEffect observes changes in the decodedImageBytes state variable and triggers the image decoding and display process within the effect block

//        if (selectedImageUri != null) {
//            Log.d("ImageNoteScreen", "Selected Image URI: $selectedImageUri")
//            AsyncImage(
//                model = selectedImageUri,
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .padding(bottom = 8.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(Color.Blue)
//            )
//        }