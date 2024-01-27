package com.example.memorise.feature_note.presentation.category

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memorise.feature_note.domain.model.Category
import com.example.memorise.feature_note.presentation.ScreenNavigations.Screens
import com.example.memorise.feature_note.presentation.category.Components.CategoryItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {

    val categoryState = viewModel.categoryState.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.onCategoryEvent(CategoriesEvent.ListCategory)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Category")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        ) { values ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                items(categoryState.categories) { category ->
                    CategoryItem(
                        category = category,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onItemClick = {
                            navController.navigate(Screens.AddEditCategoryScreen.route + "?categoryId=${category.id}")
                        },
                        onDeleteClick = {
                            viewModel.onCategoryEvent(CategoriesEvent.DeleteCategory(category))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Category Deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onCategoryEvent(CategoriesEvent.RestoreCategory)
                                }
                            }
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                IconButton(
                    onClick = {
                        navController.navigate(Screens.AddEditCategoryScreen.route)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Category",
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        }
    }
}