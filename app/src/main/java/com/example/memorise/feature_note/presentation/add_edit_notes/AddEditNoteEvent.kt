package com.example.memorise.feature_note.presentation.add_edit_notes

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{

    //data class for title event
    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()

    //data class for content event 1
    data class EnteredContent1(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus1(val focusState: FocusState) : AddEditNoteEvent()

    //data class for content event 2
    data class EnteredContent2(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus2(val focusState: FocusState) : AddEditNoteEvent()

    //data class for content event 3
    data class EnteredContent3(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus3(val focusState: FocusState) : AddEditNoteEvent()

    //data class for content event 4
    data class EnteredContent4(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus4(val focusState: FocusState) : AddEditNoteEvent()

    //data class for content event 5
    data class EnteredContent5(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus5(val focusState: FocusState) : AddEditNoteEvent()



    //data class for keyword event 1
    data class EnteredKeyword1(val value: String) : AddEditNoteEvent()
    data class ChangeKeywordFocus1(val focusState: FocusState) : AddEditNoteEvent()

    //data class for keyword event 2
    data class EnteredKeyword2(val value: String) : AddEditNoteEvent()
    data class ChangeKeywordFocus2(val focusState: FocusState) : AddEditNoteEvent()

    //data class for keyword event 3
    data class EnteredKeyword3(val value: String) : AddEditNoteEvent()
    data class ChangeKeywordFocus3(val focusState: FocusState) : AddEditNoteEvent()

    //data class for keyword event 4
    data class EnteredKeyword4(val value: String) : AddEditNoteEvent()
    data class ChangeKeywordFocus4(val focusState: FocusState) : AddEditNoteEvent()

    //data class for summary event
    data class EnteredSummary(val value: String) : AddEditNoteEvent()
    data class ChangeSummaryFocus(val focusState: FocusState) : AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()

}
