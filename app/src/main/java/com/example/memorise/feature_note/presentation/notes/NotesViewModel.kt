package com.example.memorise.feature_note.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.use_case.NotesUseCase.NoteUseCases
import com.example.memorise.feature_note.domain.util.NoteOrder
import com.example.memorise.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    //this is implemented for the flow
    private val _state = MutableStateFlow(NotesState())
    val state: Flow<NotesState> = _state.asStateFlow()

//    this it the original
//    private val _state = mutableStateOf(NotesState())
//    val state: State<NotesState> = _state

    private var recentlyDeletedNote: UnifiedNote? = null
    private var getNotesJob : Job? = null
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {
                if(_state.value.noteOrder::class == event.noteOrder::class &&
                    _state.value.noteOrder.orderType == event.noteOrder.orderType
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
    private fun updateNotesWithQuery(newQuery: String, notes: List<UnifiedNote>) {
        _state.value = _state.value.copy(
            notes = notes,
            searchQuery = if (newQuery != _state.value.searchQuery) newQuery else null
        )
    }
    private fun getNotesWithQuery(query: String) {
        getNotesJob?.cancel()
        getNotesJob = flow {
            emit(noteUseCases.searchNotes.execute(query))
        }.debounce(300)
            .onEach { notesFlow ->
                notesFlow.collect { notes ->
                    updateNotesWithQuery(query, notes)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = _state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder,
                    searchQuery = null
                )
            }
            .launchIn(viewModelScope)
    }
}

//this is the y

//this is the original getnoteswithquery
//    private fun getNotesWithQuery(query: String) {
//        getNotesJob?.cancel()
//        getNotesJob = noteUseCases.searchNotes.execute(query)
//            .onEach { notes ->
//                _state.value = state.value.copy(
//                    notes = notes,
//                    searchQuery = query,
//                )
//            }
//            .launchIn(viewModelScope)
//    }
