package com.example.memorise.feature_note.data.repository

import com.example.memorise.feature_note.data.data_source.NoteDao
import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<UnifiedNote>> {
        return dao.getAllNotes()
    }

    override suspend fun getNoteById(id: Int): UnifiedNote? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: UnifiedNote) {
        return dao.insertUnifiedNote(note)
    }

    override suspend fun deleteNote(note: UnifiedNote) {
        return dao.deleteUnifiedNote(note)
    }
}