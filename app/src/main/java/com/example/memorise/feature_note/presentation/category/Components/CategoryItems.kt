package com.example.memorise.feature_note.presentation.category.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.memorise.feature_note.domain.model.Category

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .clip(RoundedCornerShape(cornerRadius))
        .clickable { onItemClick.invoke() }
        .background(color = Color(0xFF49454F.toInt()))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        start = 12.dp,
                        bottom = 8.dp,
                        end = 8.dp
                    )
                    .fillMaxWidth(),
                text = category.categoryTitle,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDeleteClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Category"
                    )
                }
            }
        }
    }
}
