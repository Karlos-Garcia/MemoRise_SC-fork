package com.example.memorise.feature_note.presentation.add_edit_notes

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorise.feature_note.domain.model.InvalidNoteException
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.domain.model.UnifiedNote
import com.example.memorise.feature_note.domain.use_case.NotesUseCase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
    private val context: Context
) : ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteKeyword1 = mutableStateOf(NoteTextFieldState(
        hint = "Enter keyword..."
    ))
    val noteKeyword1: State<NoteTextFieldState> = _noteKeyword1

    private val _noteKeyword2 = mutableStateOf(NoteTextFieldState(
        hint = "Enter keyword..."
    ))
    val noteKeyword2: State<NoteTextFieldState> = _noteKeyword2

    private val _noteKeyword3 = mutableStateOf(NoteTextFieldState(
        hint = "Enter keyword..."
    ))
    val noteKeyword3: State<NoteTextFieldState> = _noteKeyword3

    private val _noteKeyword4 = mutableStateOf(NoteTextFieldState(
        hint = "Enter keyword..."
    ))
    val noteKeyword4: State<NoteTextFieldState> = _noteKeyword4

    private val _noteContent1 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent1: State<NoteTextFieldState> = _noteContent1

    private val _noteContent2 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent2: State<NoteTextFieldState> = _noteContent2

    private val _noteContent3 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent3: State<NoteTextFieldState> = _noteContent3

    private val _noteContent4 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent4: State<NoteTextFieldState> = _noteContent4

    private val _noteContent5 = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent5: State<NoteTextFieldState> = _noteContent5

    private val _noteSummary = mutableStateOf(NoteTextFieldState(
        hint = "Enter summary..."
    ))
    val noteSummary: State<NoteTextFieldState> = _noteSummary

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    var currentNoteType: NoteType? = null

    private val _selectedImageUri = mutableStateOf<Uri?>(null)
    val selectedImageUri: State<Uri?> = _selectedImageUri

    private val _decodedImageBytes = mutableStateOf<ByteArray?>(null)
    val decodedImageBytes: State<ByteArray?> = _decodedImageBytes

    private val _isBold = mutableStateOf(false)
    val isBold: State<Boolean> get() = _isBold

    private val _isItalic = mutableStateOf(false)
    val isItalic: State<Boolean> get() = _isItalic

    private val _isUnderlined = mutableStateOf(false)
    val isUnderlined: State<Boolean> get() = _isUnderlined

    fun toggleBold() {
        _isBold.value = !_isBold.value
    }

    fun toggleItalic() {
        _isItalic.value = !_isItalic.value
    }

    fun toggleUnderline() {
        _isUnderlined.value = !_isUnderlined.value
    }


    //retrieves the notes when noteId is not equal to -1
    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also {note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                        )
                        _noteContent1.value = noteContent1.value.copy(
                            text = note.content1 ?: "",
                        )
                        _noteContent2.value = noteContent2.value.copy(
                            text = note.content2 ?: "",
                        )
                        _noteContent3.value = noteContent3.value.copy(
                            text = note.content3 ?: "",
                        )
                        _noteContent4.value = noteContent4.value.copy(
                            text = note.content4 ?: "",
                        )
                        _noteContent5.value = noteContent5.value.copy(
                            text = note.content5 ?: "",
                        )
                        _noteKeyword1.value = noteKeyword1.value.copy(
                            text = note.keyword1 ?: "",
                        )
                        _noteKeyword2.value = noteKeyword2.value.copy(
                            text = note.keyword2 ?: "",
                        )
                        _noteKeyword3.value = noteKeyword3.value.copy(
                            text = note.keyword3 ?: "",
                        )
                        _noteKeyword4.value = noteKeyword4.value.copy(
                            text = note.keyword4 ?: "",
                        )
                        _noteSummary.value = noteSummary.value.copy(
                            text = note.summary ?: "",
                        )
                        _decodedImageBytes.value = note.imageBytes
                    }
                }
            }
        }
    }
    fun onNoteTypeSelected(noteType: NoteType) {
        currentNoteType = noteType
    }
    private suspend fun decodeImageBytesFromUri(uri: Uri): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()
                bytes
            } catch (e: Exception) {
                null
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredContent1 -> {
                _noteContent1.value = _noteContent1.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredContent2 -> {
                _noteContent2.value = _noteContent2.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredContent3 -> {
                _noteContent3.value = _noteContent3.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredContent4 -> {
                _noteContent4.value = _noteContent4.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredContent5 -> {
                _noteContent5.value = _noteContent5.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredKeyword1 -> {
                _noteKeyword1.value = _noteKeyword1.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredKeyword2 -> {
                _noteKeyword2.value = _noteKeyword2.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredKeyword3 -> {
                _noteKeyword3.value = _noteKeyword3.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredKeyword4 -> {
                _noteKeyword4.value = _noteKeyword4.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredSummary -> {
                _noteSummary.value = _noteSummary.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredNoteType -> {
                currentNoteType = event.noteType
            }
            is AddEditNoteEvent.ImageSelected -> {
                _selectedImageUri.value = event.uri
                viewModelScope.launch {
                    val imageBytes = decodeImageBytesFromUri(event.uri)
                    if (imageBytes != null) {
                        _decodedImageBytes.value = imageBytes
                    }
                }
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
                                summary = noteSummary.value.text,
                                timestamp = System.currentTimeMillis(),
                                noteType = currentNoteType ?: NoteType.BASIC,
                                imageBytes = _decodedImageBytes.value,
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