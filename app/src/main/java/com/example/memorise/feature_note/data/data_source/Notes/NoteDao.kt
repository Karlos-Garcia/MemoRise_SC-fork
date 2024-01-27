package com.example.memorise.feature_note.data.data_source.Notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memorise.feature_note.domain.model.UnifiedNote
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM UnifiedNote")
    fun getAllNotes(): Flow<List<UnifiedNote>>

    @Query("SELECT * FROM UnifiedNote WHERE id = :id")
    suspend fun getNoteById(id: Int): UnifiedNote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnifiedNote(note: UnifiedNote)

    @Delete
    suspend fun deleteUnifiedNote(note: UnifiedNote)

    @Query ("SELECT * FROM UnifiedNote WHERE title LIKE '%' || :query || '%' ")
    fun searchNotes (query: String): Flow<List<UnifiedNote>>
}