package com.example.memorise.feature_note.data.repository

import com.example.memorise.feature_note.data.data_source.CategoryDao
import com.example.memorise.feature_note.data.data_source.FolderDao
import com.example.memorise.feature_note.data.data_source.NoteDao
import com.example.memorise.feature_note.domain.model.Category
import com.example.memorise.feature_note.domain.model.Folder
import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val categoryDao: CategoryDao,
    private val folderDao: FolderDao,
) : NoteRepository {
    override fun getAllNotes(): Flow<List<UnifiedNote>> {
        return noteDao.getAllNotes()
    }
    override suspend fun getNoteById(id: Int): UnifiedNote? {
        return noteDao.getNoteById(id)
    }
    override suspend fun insertNote(note: UnifiedNote) {
        return noteDao.insertUnifiedNote(note)
    }
    override suspend fun deleteNote(note: UnifiedNote) {
        return noteDao.deleteUnifiedNote(note)
    }
    override fun searchNotes(query: String): Flow<List<UnifiedNote>> {
        return noteDao.searchNotes(query)
    }
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }
    override suspend fun getCategoryById(id: Int): Category? {
        return categoryDao.getCategoryById(id)
    }
    override suspend fun insertCategory(category: Category) {
        return categoryDao.insertCategory(category)
    }
    override suspend fun deleteCategory(category: Category) {
        return categoryDao.deleteCategory(category)
    }
    override fun getAllFolders(): Flow<List<Folder>> {
        return folderDao.getAllFolders()
    }
    override suspend fun getFolderById(id: Int): Folder? {
        return folderDao.getFolderById(id)
    }
    override suspend fun insertFolder(folder: Folder) {
        return folderDao.insertFolder(folder)
    }
    override suspend fun deleteFolder(folder: Folder) {
        return folderDao.deleteFolder(folder)
    }
}