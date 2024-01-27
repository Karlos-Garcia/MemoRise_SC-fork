package com.example.memorise.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UnifiedNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val keyword1: String? = null,
    val keyword2: String? = null,
    val keyword3: String? = null,
    val keyword4: String? = null,
    val content1: String? = null,
    val content2: String? = null,
    val content3: String? = null,
    val content4: String? = null,
    val content5: String? = null,
    val summary: String? = null,
    val category: Int? = null,
    val noteType: NoteType, // Enum or string to identify the type
    val timestamp: Long = System.currentTimeMillis()
)

enum class NoteType {
    BASIC, CORNELL, OUTLINE, CHARTING, QUADRANT, LADDER,
}

class InvalidNoteException(message: String): Exception(message)
