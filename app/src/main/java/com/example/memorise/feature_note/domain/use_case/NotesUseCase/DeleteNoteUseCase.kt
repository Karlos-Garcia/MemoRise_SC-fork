package com.example.memorise.feature_note.domain.use_case.NotesUseCase

import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: UnifiedNote) {
        repository.deleteNote(note)
    }
}