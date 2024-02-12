package com.example.memorise.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.memorise.ui.screens.noteScreens.component.ShowInformationDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topappbar(
    navController: NavController,
    name: String,
    dialogTitle: String = "",
    dialogText: String = "",
    showInformationIcon: Boolean = false,
    showInformationDialog: Boolean = false,
    onInformationClick: () -> Unit = {},
    onDismiss: () -> Unit,
    onBackClicked: () -> Unit,
    content: @Composable () -> Unit,
) {
    val showAlertDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(name)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            showAlertDialog.value = true
//                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        if (showInformationIcon) {
                            IconButton(
                                onClick = onInformationClick
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Note information"
                                )
                            }
                            if (showInformationDialog) {
                                ShowInformationDialog(dialogTitle, dialogText, onDismiss)
                            }
                        }

                    }

                )
            }
        ){
            content()
        }
    }

    if (showAlertDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the alert dialog
                showAlertDialog.value = false
            },
            title = {
                Text(text = "Confirmation")
            },
            text = {
                Text("Are you sure you want to go back without saving?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Dismiss the alert dialog and navigate back
                        showAlertDialog.value = false
                        onBackClicked()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Dismiss the alert dialog
                        showAlertDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}