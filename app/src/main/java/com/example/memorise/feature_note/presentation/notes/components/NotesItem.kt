package com.example.memorise.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.domain.model.UnifiedNote
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteItem(
    note: UnifiedNote,
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
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        bottom = 8.dp,
                        end = 8.dp
                    ),
                text = note.content1 ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(12.dp),
                    text = formatTimestamp(note.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                )
                var noteTypetext = when (note.noteType) {
                    NoteType.BASIC -> "Basic Note"
                    NoteType.QUADRANT -> "Quadrant Note"
                    NoteType.LADDER -> "Ladder Note"
                    NoteType.CHARTING -> "Charting Note"
                    NoteType.OUTLINE -> "Outline Note"
                    NoteType.CORNELL -> "Cornell Note"
                }
                Text(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp
                        ),
                    text = noteTypetext,
                    style = MaterialTheme.typography.bodySmall,
                )
                IconButton(
                    onClick = onDeleteClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Note"
                    )
                }
            }
        }
    }
}


private fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(date)
}