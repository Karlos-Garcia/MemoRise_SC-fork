package com.example.memorise.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memorise.feature_note.domain.model.UnifiedNote

//add type 4 to entities
@Database(
    entities = [UnifiedNote::class],
    version = 1,
    exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}