package com.example.memorise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.memorise.ui.getNavigationItems
import com.example.memorise.ui.mainScreen
import com.example.memorise.ui.theme.MemoRiseTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoRiseTheme {
                mainScreen(items = getNavigationItems())
//                CornellNote()
//                basicNote()
//                MainScreen(items = getNavigationItems())
//                settingScreen()
            }
        }
    }
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