package com.example.memorise.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memorise.ui.Screens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun aboutUsScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "About Us")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(Screens.MainScreen.route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        ){
            aboutUs()
        }
    }
}

@Composable
fun aboutUs(
    modifier: Modifier = Modifier

) {

    Column(
        modifier = modifier
            .padding(
                start = 32.dp,
                end = 32.dp,
                top = 72.dp,
                bottom = 72.dp
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text("MemoRise is an application developed " +
                "by Karlos Louis Angelo Garcia and Archie Bayani to " +
                "improve the Cavite State University students improve " +
                "their note taking methods",
            fontSize= 24.sp,
            textAlign = TextAlign
                .Start
        )
        Text("apple")
        Text("apple")
    }


}