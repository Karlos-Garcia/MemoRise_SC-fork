package com.example.memorise.feature_note.presentation.notes

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.presentation.notes.components.NavigationItem
import com.example.memorise.feature_note.presentation.notes.components.getNavigationItems
import com.example.memorise.feature_note.presentation.ScreenNavigations.Screens
import com.example.memorise.feature_note.presentation.add_edit_notes.AddEditNoteViewModel
import com.example.memorise.feature_note.presentation.notes.components.NoteItem
import com.example.memorise.feature_note.presentation.notes.components.OrderSection
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    items: List<NavigationItem>,
    viewModel: NotesViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val items = getNavigationItems(navController = navController)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        var showButtonList by remember {
            mutableStateOf(false)
        }

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "MemoRise",
                        modifier = Modifier
                            .padding(
                                start = 60.dp,
                                bottom = 20.dp
                            ),
                        fontSize = 20.sp
                    )
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(

                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                item.route(navController)
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                modifier = Modifier,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "MemoRise")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Burger Menu"
                                )
                            }
                        },
                        actions = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.width(52.dp))

                                TextField(

                                    value = state.searchQuery ?: "",
                                    onValueChange = { newQuery ->
                                        viewModel.onEvent(NotesEvent.Search(newQuery))
                                    },
                                    label = { Text("Search Notes") },
                                    modifier = Modifier
                                        .weight(1f) // Takes up remaining horizontal space
                                        .clip(RoundedCornerShape(16.dp))
                                )
                                Box(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .height(56.dp) // Adjust height as needed
                                ) {
                                    OrderSection(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        noteOrder = state.noteOrder,
                                        onOrderChange = {
                                            viewModel.onEvent(NotesEvent.Order(it))
                                        }
                                    )
                                }
                            }
                        }
                    )
                },
            ) { values ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(values)
                ) {
                    items(state.notes) { note ->
                        NoteItem(
                            note = note,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onItemClick = {
                                val route = when (note.noteType) {
                                    NoteType.BASIC -> Screens.BasicNoteScreen.route + "?noteId=${note.id}"
                                    NoteType.CORNELL -> Screens.CornellNoteScreen.route + "?noteId=${note.id}"
                                    NoteType.QUADRANT -> Screens.QuadrantNoteScreen.route + "?noteId=${note.id}"
                                    NoteType.OUTLINE -> Screens.OutlineNoteScreen.route + "?noteId=${note.id}"
                                    NoteType.LADDER -> Screens.LadderNoteScreen.route + "?noteId=${note.id}"
                                    NoteType.IMAGE -> Screens.ImageNoteScreen.route + "?noteId=${note.id}"
                                }
                                navController.navigate(route)
                            },
                            onDeleteClick = {
                                viewModel.onEvent(NotesEvent.DeleteNote(note))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Note Deleted",
                                        actionLabel = "Undo"
                                    )
                                    if(result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(NotesEvent.RestoreNote)
                                    }
                                }
                            }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                IconButton(
                    onClick = { showButtonList = !showButtonList },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier
                            .size(40.dp)
                    )
                    DropdownMenu(
                        expanded = showButtonList,
                        onDismissRequest = { showButtonList = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("Add new note") },
                            onClick = { navController.navigate(Screens.NoteSelectionScreen.route) }
                        )
                        DropdownMenuItem(
                            text = { Text("Add new image") },
                            onClick = {
                                navController.navigate(Screens.ImageNoteScreen.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Add new folder") },
                            onClick = { /*TODO*/ }
                        )
                    }
                }
            }
        }
    }
}
