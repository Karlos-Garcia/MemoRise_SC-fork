package com.example.memorise.feature_note.domain.repository

import com.example.memorise.feature_note.domain.model.Category
import com.example.memorise.feature_note.domain.model.Folder
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

    fun getAllFolders(): Flow<List<Folder>>
    suspend fun getFolderById(id: Int): Folder?
    suspend fun insertFolder(folder: Folder)
    suspend fun deleteFolder(folder: Folder)
}
