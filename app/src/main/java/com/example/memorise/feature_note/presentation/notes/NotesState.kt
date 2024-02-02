package com.example.memorise.feature_note.presentation.notes

import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.util.NoteOrder
import com.example.memorise.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<UnifiedNote> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val searchQuery: String? = null,
    val recentlyDeletedNote: UnifiedNote? = null
)