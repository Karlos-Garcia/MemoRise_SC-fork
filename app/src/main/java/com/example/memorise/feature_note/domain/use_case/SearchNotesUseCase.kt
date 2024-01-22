package com.example.memorise.feature_note.domain.use_case

import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class SearchNotesUseCase(
    private val repository: NoteRepository
) {
    fun execute(query: String): Flow<List<UnifiedNote>> {
        return repository.searchNotes(query)
    }
}