package com.example.memorise.feature_note.domain.repository

import com.example.memorise.feature_note.domain.model.Category
import com.example.memorise.feature_note.domain.model.UnifiedNote
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<UnifiedNote>>

    suspend fun getNoteById(id: Int): UnifiedNote?

    suspend fun insertNote(note: UnifiedNote)

    suspend fun deleteNote(note: UnifiedNote)

    fun searchNotes(query: String): Flow<List<UnifiedNote>>

    fun getAllCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Int): Category?
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}
