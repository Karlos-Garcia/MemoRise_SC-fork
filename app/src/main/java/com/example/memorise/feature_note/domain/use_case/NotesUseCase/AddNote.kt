package com.example.memorise.feature_note.domain.use_case.NotesUseCase

import com.example.memorise.feature_note.domain.model.InvalidNoteException
import com.example.memorise.feature_note.domain.model.Note
import com.example.memorise.feature_note.domain.repository.NoteRepository

class AddNote (
    private val repository: NoteRepository
){
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note, categoryId: Int?, folderId: Int?) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty")
        }
        repository.insertNote(note)
    }
}