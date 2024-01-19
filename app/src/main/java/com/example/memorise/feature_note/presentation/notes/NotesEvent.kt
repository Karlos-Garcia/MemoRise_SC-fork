package com.example.memorise.feature_note.presentation.notes

import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: UnifiedNote): NotesEvent()
    object RestoreNote: NotesEvent()
//    object ToggleOrderSection: NotesEvent()
}
