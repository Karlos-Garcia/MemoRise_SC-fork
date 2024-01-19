package com.example.memorise.ui.screens.noteScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.memorise.ui.screens.Topappbar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuadrantNote(
    navController: NavController
) {
    val navController = rememberNavController()

    Topappbar (
        navController = navController,
        name = "Cornell Note"
    ) {
        QuadrantTextFields()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuadrantTextFields(

) {

    var topLeftTitle by remember { mutableStateOf("") }
    var topLeftContent by remember { mutableStateOf("") }
    var topRightTitle by remember { mutableStateOf("") }
    var topRightContent by remember { mutableStateOf("") }
    var bottomLeftTitle by remember { mutableStateOf("") }
    var bottomLeftContent by remember { mutableStateOf("") }
    var bottomRightTitle by remember { mutableStateOf("") }
    var bottomRightContent by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 76.dp,
                start = 4.dp,
                bottom = 4.dp,
                end = 4.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
        ) {
            Column() {
                TextField(
                    singleLine = true,
                    label= {Text(text = "Title")},
                    value = topLeftTitle,
                    onValueChange = {topLeftTitle = it},
                    modifier = Modifier
                        .padding(
                            bottom = 4.dp,
                            end = 2.dp
                        )
                        .fillMaxWidth(0.5f)

                )
                TextField(
                    label= {Text(text = "Content")},
                    value = topLeftContent,
                    onValueChange = {topLeftContent = it},
                    modifier = Modifier
                        .padding(
                            end = 4.dp,
                            bottom = 4.dp
                        )
                        .fillMaxWidth(0.5f)
                        .height(340.dp)
//                        .fillMaxWidth(0.5f)
//                        .fillMaxHeight(0.45f)
                )
            }
            Column() {
                TextField(
                    singleLine = true,
                    label= {Text(text = "Title")},
                    value = topRightTitle,
                    onValueChange = {topRightTitle = it},
                            modifier = Modifier
                                .padding(
                                    bottom = 4.dp
                                )
                            .fillMaxWidth(1f)

                )
                TextField(
                    label= {Text(text = "Content")},
                    value = topRightContent,
                    onValueChange = {topRightContent = it},
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(340.dp)
                )
            }
        }
        Row {
            Column() {
                TextField(
                    singleLine = true,
                    label= {Text(text = "Title")},
                    value = bottomLeftTitle,
                    onValueChange = {bottomLeftTitle = it},
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(
                            bottom = 4.dp,
                            end = 2.dp
                        )
                )
                TextField(
                    label= {Text(text = "Content")},
                    value = bottomLeftContent,
                    onValueChange = {bottomLeftContent = it},
                    modifier = Modifier
                        .padding(
                            end = 4.dp
                        )
                        .fillMaxWidth(0.5f)
                        .height(340.dp)
                )
            }
            Column() {
                TextField(
                    singleLine = true,
                    label= {Text(text = "Title")},
                    value = bottomRightTitle,
                    onValueChange = {bottomRightTitle = it},
                    modifier = Modifier
                        .padding(
                            bottom = 4.dp
                        )
                        .fillMaxWidth(1f)
                )
                TextField(
                    label= {Text(text = "Content")},
                    value = bottomRightContent,
                    onValueChange = {bottomRightContent = it},
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(340.dp)
                )
            }
        }
    }
}
