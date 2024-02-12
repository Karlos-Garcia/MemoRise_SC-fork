package com.example.memorise.ui.screens.noteScreens.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ShowInformationDialog(
    dialogTitle: String = "",
    dialogText: String = "",
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "Close")
            }
        },
        title ={ Text(dialogTitle)} ,
        text = { Text(dialogText)},
    )
}