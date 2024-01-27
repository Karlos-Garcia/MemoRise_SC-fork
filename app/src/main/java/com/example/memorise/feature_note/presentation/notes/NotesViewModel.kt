package com.example.memorise.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.use_case.NotesUseCase.NoteUseCases
import com.example.memorise.feature_note.domain.util.NoteOrder
import com.example.memorise.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state


    private var recentlyDeletedNote: UnifiedNote? = null
    private var getNotesJob : Job? = null
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {
                if(state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                    ) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.Search -> {
                getNotesWithQuery(event.query)
            }
        }
    }

    //making this work
    private fun getNotesWithQuery(query: String) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.searchNotes.execute(query)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    searchQuery = query,
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder,
                    //newly added searchQuery
                    searchQuery = null
                )
            }
            .launchIn(viewModelScope)
    }
}