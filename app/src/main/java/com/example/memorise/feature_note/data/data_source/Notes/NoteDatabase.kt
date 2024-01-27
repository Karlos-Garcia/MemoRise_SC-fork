package com.example.memorise.feature_note.data.data_source.Notes

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memorise.feature_note.data.data_source.Category.CategoryDao
import com.example.memorise.feature_note.domain.model.Category
import com.example.memorise.feature_note.domain.model.UnifiedNote

@Database(
    entities = [UnifiedNote::class, Category::class],
    version = 1,
    exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val categoryDao: CategoryDao
    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}