package com.example.memorise.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorise.ui.screens.CornellNote
import com.example.memorise.ui.screens.QuadrantNote
import com.example.memorise.ui.screens.aboutUsScreen
import com.example.memorise.ui.screens.basicNote
import com.example.memorise.ui.screens.getNavigationItems
import com.example.memorise.ui.screens.mainScreen
import com.example.memorise.ui.screens.settingScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route
    ) {
        composable(
            route = Screens.MainScreen.route,
        ) {
            mainScreen(
                navController = navController,
                items = getNavigationItems(navController),
            )
        }
        composable(
            route = Screens.SettingScreen.route,
        ) {
            settingScreen(
                navController = navController
            )
        }
        composable(
            route = Screens.AboutUsScreen.route,
        ) {
            aboutUsScreen(
                navController = navController
            )
        }
        composable(
            route = Screens.BasicNoteScreen.route,
        ) {
            basicNote(
                navController = navController
            )
        }
        composable(
            route = Screens.ChartingNoteScreen.route,
        ) {
            basicNote(
                navController = navController
            )
        }
        composable(
            route = Screens.CornellNoteScreen.route,
        ) {
            CornellNote(
                navController = navController
            )
        }
        composable(
            route = Screens.OutlineNoteScreen.route,
        ) {
            basicNote(
                navController = navController
            )
        }
        composable(
            route = Screens.QuadrantNoteScreen.route,
        ) {
            QuadrantNote(
                navController = navController
            )
        }
    }
}

