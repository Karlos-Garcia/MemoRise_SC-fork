package com.example.memorise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.memorise.ui.theme.MemoRiseTheme
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
    //make a val for the link navigation
)

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoRiseTheme {
//                CornellNote()
//                basicNote()
                mainScreen(items = getNavigationItems())
//                settingScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(items: List<NavigationItem>) {
    //can put the nav links per selection?
    //this items variable calls the values within the getNavigationItems() function
    val items = getNavigationItems()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                //navController.navigate(item.route)
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
                            badge = {
                                item.badgeCount?.let {
                                    Text(text = item.badgeCount.toString())
                                }
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
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                            homeMoreVert()
                        }
                    )
                }
            ) { values ->
                LazyColumn(
                    modifier = Modifier
                        //fill max size makes the list take the
                        // whole horizontal spaces per items
                        .fillMaxSize()
                        .padding(values)
                ) {
                    items(100) {
                        Text(text = "Note number $it",
                            modifier = Modifier
                                .padding(16.dp))
                    }
                }
            }
        }
        addButton()
    }
}



//this function gets a list for the vertical menu within the main screen, the three dots with the sort by, view by, and categories
@Composable
fun homeMoreVert(
) {
    var showMoreVert by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { showMoreVert = !showMoreVert }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More Options"
        )
    }
    DropdownMenu(
        expanded = showMoreVert,
        onDismissRequest = { showMoreVert = false }) {
        DropdownMenuItem(
            text = {Text("Sort by")},
            onClick = {}
        )
//            onClick = {})
        DropdownMenuItem(
            text = {Text("View by")},
            onClick = {}
        )
        DropdownMenuItem(
            text = {Text("Categories")},
            onClick = {}
        )
    }
}

@Composable
fun addButton () {
    var showButtonList by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconButton(onClick = {showButtonList = !showButtonList},
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
                onDismissRequest = {showButtonList = false},
            ) {
//                DropdownMenuItem(
//                    text = { Text("Add new note")},
//                    onClick = {}
//                )
                addNewNote()
                DropdownMenuItem(
                    text = { Text("Add new image")},
                    onClick = { /*TODO*/ }
                )
                DropdownMenuItem(
                    text = { Text("Add new folder")},
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}

//this functions list all the note taking methods after selecting "Add new note" from the + selection in main screen
@Composable
fun addNewNote() {
    var AddNewNotes by remember{
        mutableStateOf(false)
    }
        DropdownMenuItem(
            text = { Text("Add new note")},
            onClick = {AddNewNotes = !AddNewNotes}
        )
    Box(modifier = Modifier
        .fillMaxWidth()
        ,
    ) {DropdownMenu(
        expanded = AddNewNotes,
        onDismissRequest = {AddNewNotes = false},

    )
        {
            DropdownMenuItem(
                text = { Text("Basic Note") },
                onClick = {}
            )
            DropdownMenuItem(
                text = { Text("Cornell Note Method") },
                onClick = {}
            )
            DropdownMenuItem(
                text = { Text("Outline Note Method") },
                onClick = {}
            )
            DropdownMenuItem(
                text = { Text("Charting Note Method") },
                onClick = {}
            )
            DropdownMenuItem(
                text = { Text("Quadrant Note Method") },
                onClick = {}
            )
        }
    }
}


//function that calls on the list of navigation items and calls them to the main activity
fun getNavigationItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = "notes",
            selectedIcon = Icons.Filled.Create,
            unselectedIcon = Icons.Outlined.Create
        ),
        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
        NavigationItem(
            title = "About Us",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
//            badgeCount = 69
        )
    )
}

//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun MemorisePreview() {
//    MemoRiseTheme {
//        addNewNote()
//    }
//}