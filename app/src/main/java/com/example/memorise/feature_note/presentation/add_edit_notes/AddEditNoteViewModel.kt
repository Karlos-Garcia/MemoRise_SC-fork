package com.example.memorise.feature_note.presentation.add_edit_notes

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorise.feature_note.domain.model.Category
import com.example.memorise.feature_note.domain.model.FormattedSegment
import com.example.memorise.feature_note.domain.model.InvalidNoteException
import com.example.memorise.feature_note.domain.model.NoteType
import com.example.memorise.feature_note.domain.model.Note
import com.example.memorise.feature_note.domain.use_case.CategoryUseCase.CategoryUseCases
import com.example.memorise.feature_note.domain.use_case.NotesUseCase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCase: CategoryUseCases,
    savedStateHandle: SavedStateHandle,
    private val context: Context
) : ViewModel() {
    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val noteTitle: State<NoteTextFieldState> = _noteTitle
    private val _noteKeyword1 = mutableStateOf(NoteTextFieldState(hint = "Enter keyword..."))
    val noteKeyword1: State<NoteTextFieldState> = _noteKeyword1
    private val _noteKeyword2 = mutableStateOf(NoteTextFieldState(hint = "Enter keyword..."))
    val noteKeyword2: State<NoteTextFieldState> = _noteKeyword2
    private val _noteKeyword3 = mutableStateOf(NoteTextFieldState(hint = "Enter keyword..."))
    val noteKeyword3: State<NoteTextFieldState> = _noteKeyword3
    private val _noteKeyword4 = mutableStateOf(NoteTextFieldState(hint = "Enter keyword..."))
    val noteKeyword4: State<NoteTextFieldState> = _noteKeyword4
    private val _noteContent1 = mutableStateOf(NoteTextFieldState(hint = "Enter content..."))
    val noteContent1: State<NoteTextFieldState> = _noteContent1
    private val _noteContent2 = mutableStateOf(NoteTextFieldState(hint = "Enter content..."))
    val noteContent2: State<NoteTextFieldState> = _noteContent2
    private val _noteContent3 = mutableStateOf(NoteTextFieldState(hint = "Enter content..."))
    val noteContent3: State<NoteTextFieldState> = _noteContent3
    private val _noteContent4 = mutableStateOf(NoteTextFieldState(hint = "Enter content..."))
    val noteContent4: State<NoteTextFieldState> = _noteContent4
    private val _noteContent5 = mutableStateOf(NoteTextFieldState(hint = "Enter content..."))
    val noteContent5: State<NoteTextFieldState> = _noteContent5
    private val _noteSummary = mutableStateOf(NoteTextFieldState(hint = "Enter summary..."))
    val noteSummary: State<NoteTextFieldState> = _noteSummary

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    var currentNoteType: NoteType? = null

    private val _selectedImageUri = mutableStateOf<Uri?>(null)
    val selectedImageUri: State<Uri?> = _selectedImageUri

    private val _decodedImageBytes = mutableStateOf<ByteArray?>(null)
    val decodedImageBytes: State<ByteArray?> = _decodedImageBytes

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory

    val categories: Flow<List<Category>> = categoryUseCase.getCategoryList()

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
    }
     //this is now all belong to text formatting:

    private val _isBold = mutableStateOf(false)
    val isBold: State<Boolean> get() = _isBold
    private val _isItalic = mutableStateOf(false)
    val isItalic: State<Boolean> get() = _isItalic
    private val _isUnderlined = mutableStateOf(false)
    val isUnderlined: State<Boolean> get() = _isUnderlined

    fun toggleBold() {
        _isBold.value = !_isBold.value
        updateFormatting()
    }
    fun toggleItalic() {
        _isItalic.value = !_isItalic.value
        updateFormatting()
    }
    fun toggleUnderline() {
        _isUnderlined.value = !_isUnderlined.value
        updateFormatting()
    }

    private fun updateSegments(existingSegments: List<FormattedSegment>, newSegment: FormattedSegment): List<FormattedSegment> {
        val updatedSegments = mutableListOf<FormattedSegment>()
        return updatedSegments
    }

    private val _selectedTextRanges: MutableMap<Int, Pair<Int, Int>> = mutableMapOf()

    fun onTextSelectionChange(textFieldId: Int, startIndex: Int, endIndex: Int) {
        _selectedTextRanges[textFieldId] = startIndex to endIndex
        updateFormatting()
    }

    private fun updateFormatting() {
        viewModelScope.launch {
            for (textFieldId in _selectedTextRanges.keys) {
                val selectedText = getSelectedText(textFieldId) ?: continue
                val newSegment = FormattedSegment(
                    text = selectedText,
                    isBold = isBold.value,
                    isItalic = isItalic.value,
                    isUnderlined = isUnderlined.value,
                    start = _selectedTextRanges[textFieldId]?.first ?: continue,
                    end = _selectedTextRanges[textFieldId]?.second ?: continue,
                    fieldId = textFieldId
                )
            }
        }
    }


    private fun getTextForTextField(textFieldId: Int): String? {
        return when (textFieldId) {
            1 -> noteTitle.value.text
            2 -> noteContent1.value.text
            3 -> noteContent2.value.text
            4 -> noteContent3.value.text
            5 -> noteContent4.value.text
            6 -> noteContent5.value.text
            7 -> noteKeyword1.value.text
            8 -> noteKeyword2.value.text
            9 -> noteKeyword3.value.text
            10 -> noteKeyword4.value.text
            11 -> noteSummary.value.text
            else -> null
        }
    }

    private fun getSelectedText(textFieldId: Int): String? {
        val text = getTextForTextField(textFieldId) ?: return null
        val (startIndex, endIndex) = _selectedTextRanges[textFieldId] ?: return ""
        return text.substring(startIndex, endIndex)
    }

    private val _selectedStartTitle = mutableStateOf(-1)
    val selectedStartTitle: State<Int> get() = _selectedStartTitle

    private val _selectedEndTitle = mutableStateOf(-1)
    val selectedEndTitle: State<Int> get() = _selectedEndTitle

    private val _selectedStartContent = mutableStateOf(-1)
    val selectedStartContent: State<Int> get() = _selectedStartContent

    private val _selectedEndContent = mutableStateOf(-1)
    val selectedEndContent: State<Int> get() = _selectedEndContent

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
            is AddEditNoteEvent.ToggleBold -> {
                toggleBold()
            }
            is AddEditNoteEvent.ToggleItalic -> {
                toggleItalic()
            }
            is AddEditNoteEvent.ToggleUnderline -> {
                toggleUnderline()
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        val selectedCategoryId = _selectedCategory.value?.id

                        val formattedSegments = mutableListOf<FormattedSegment>()
                        for (textFieldId in _selectedTextRanges.keys) {
                            val selectedText = getSelectedText(textFieldId) ?: continue
                            val newSegment = FormattedSegment(
                                text = selectedText,
                                isBold = isBold.value,
                                isItalic = isItalic.value,
                                isUnderlined = isUnderlined.value,
                                start = _selectedTextRanges[textFieldId]?.first ?: continue,
                                end = _selectedTextRanges[textFieldId]?.second ?: continue,
                                fieldId = textFieldId
                            )
                            formattedSegments.add(newSegment)
                        }

                        noteUseCases.addNote(
                            Note(
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
                                category_id = selectedCategoryId ?: -1,
                                timestamp = System.currentTimeMillis(),
                                noteType = currentNoteType ?: NoteType.BASIC,
                                imageBytes = _decodedImageBytes.value,
                                id = currentNoteId,
                                segments = formattedSegments
                            ),
                        selectedCategoryId ?: -1
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