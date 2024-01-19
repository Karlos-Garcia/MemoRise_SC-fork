package com.example.memorise.feature_note.presentation.add_edit_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorise.feature_note.domain.model.InvalidNoteException
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    //keyword 1
    private val _noteKeyword1 = mutableStateOf(NoteTextFieldState(
        hint = "Enter keyword..."
    ))
    val noteKeyword1: State<NoteTextFieldState> = _noteKeyword1

    //keyword 2
    private val _noteKeyword2 = mutableStateOf(NoteTextFieldState(
        hint = "Enter keyword..."
    ))
    val noteKeyword2: State<NoteTextFieldState> = _noteKeyword2

    //keyword 3
    private val _noteKeyword3 = mutableStateOf(NoteTextFieldState(
        hint = "Enter keyword..."
    ))
    val noteKeyword3: State<NoteTextFieldState> = _noteKeyword3

    //keyword 4
    private val _noteKeyword4 = mutableStateOf(NoteTextFieldState(
        hint = "Enter keyword..."
    ))
    val noteKeyword4: State<NoteTextFieldState> = _noteKeyword4

    //content 1
    private val _noteContent1 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent1: State<NoteTextFieldState> = _noteContent1

    //content 2
    private val _noteContent2 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent2: State<NoteTextFieldState> = _noteContent2

    //content 3
    private val _noteContent3 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent3: State<NoteTextFieldState> = _noteContent3

    //content 4
    private val _noteContent4 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent4: State<NoteTextFieldState> = _noteContent4

    //content 5
    private val _noteContent5 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent5: State<NoteTextFieldState> = _noteContent5

    //summary
    private val _noteSummary = mutableStateOf(NoteTextFieldState(
        hint = "Enter summary..."
    ))
    val noteSummary: State<NoteTextFieldState> = _noteSummary

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    var currentNoteType: NoteType? = null
    fun onNoteTypeSelected(noteType: NoteType) {
        currentNoteType = noteType
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            //title
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }

            //content1
            is AddEditNoteEvent.EnteredContent1 -> {
                _noteContent1.value = _noteContent1.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus1 -> {
                _noteContent1.value = _noteContent1.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent1.value.text.isBlank()
                )
            }

            //content2
            is AddEditNoteEvent.EnteredContent2 -> {
                _noteContent2.value = _noteContent2.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus2 -> {
                _noteContent2.value = _noteContent2.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent2.value.text.isBlank()
                )
            }

            //content3
            is AddEditNoteEvent.EnteredContent3 -> {
                _noteContent3.value = _noteContent3.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus3 -> {
                _noteContent3.value = _noteContent3.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent3.value.text.isBlank()
                )
            }

            //content4
            is AddEditNoteEvent.EnteredContent4 -> {
                _noteContent4.value = _noteContent4.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus4 -> {
                _noteContent4.value = _noteContent4.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent4.value.text.isBlank()
                )
            }

            //content5
            is AddEditNoteEvent.EnteredContent5 -> {
                _noteContent5.value = _noteContent5.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus5 -> {
                _noteContent5.value = _noteContent5.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent5.value.text.isBlank()
                )
            }

            //keyword1
            is AddEditNoteEvent.EnteredKeyword1 -> {
                _noteKeyword1.value = _noteKeyword1.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeKeywordFocus1 -> {
                _noteKeyword1.value = _noteKeyword1.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteKeyword1.value.text.isBlank()
                )
            }

            //keyword2
            is AddEditNoteEvent.EnteredKeyword2 -> {
                _noteKeyword2.value = _noteKeyword2.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeKeywordFocus2 -> {
                _noteKeyword2.value = _noteKeyword2.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteKeyword2.value.text.isBlank()
                )
            }

            //keyword3
            is AddEditNoteEvent.EnteredKeyword3 -> {
                _noteKeyword3.value = _noteKeyword3.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeKeywordFocus3 -> {
                _noteKeyword3.value = _noteKeyword3.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteKeyword3.value.text.isBlank()
                )
            }

            //keyword4
            is AddEditNoteEvent.EnteredKeyword4 -> {
                _noteKeyword4.value = _noteKeyword4.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeKeywordFocus4 -> {
                _noteKeyword4.value = _noteKeyword4.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteKeyword4.value.text.isBlank()
                )
            }

            //Summary
            is AddEditNoteEvent.EnteredSummary -> {
                _noteSummary.value = _noteSummary.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeSummaryFocus -> {
                _noteSummary.value = _noteSummary.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteSummary.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            UnifiedNote(
                                title = noteTitle.value.text,
                                keyword1 = noteKeyword1.value.text,
                                keyword2 = noteKeyword2.value.text,
                                keyword3 = noteKeyword3.value.text,
                                keyword4 = noteKeyword4.value.text,
                                content1 = noteContent1.value.text,
                                content2 = noteContent2.value.text,
                                content3 = noteContent3.value.text,
                                content4 = noteContent4.value.text,
                                content5 = noteContent5.value.text,
                                timestamp = System.currentTimeMillis(),
                                noteType = currentNoteType ?: NoteType.BASIC,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch(e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }
    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}