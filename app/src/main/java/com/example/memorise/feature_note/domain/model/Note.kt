package com.example.memorise.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UnifiedNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val keyword1: String? = null,
    val keyword2: String? = null,
    val keyword3: String? = null,
    val keyword4: String? = null,
    val content1: String? = null,
    val content2: String? = null,
    val content3: String? = null,
    val content4: String? = null,
    val content5: String? = null,
    val summary: String? = null,
    val noteType: NoteType, // Enum or string to identify the type
    val timestamp: Long = System.currentTimeMillis()
)

enum class NoteType {
    BASIC, CORNELL, OUTLINE, CHARTING, QUADRANT, LADDER
}


class InvalidNoteException(message: String): Exception(message)

//@Entity
//data class BasicNote(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    val title: String,
//    val content: String,
//    val timestamp: Long,
//)
//
//@Entity
//data class CornellNote(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    val title: String,
//    val keyword1: String,
//    val content1: String,
//    val keyword2: String,
//    val content2: String,
//    val summary: String,
//    val timestamp: Long
//)
//
//@Entity
//data class OutlineNote(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    val title: String,
//    val keyword1: String,
//    val content1: String,
//    val keyword2: String,
//    val content2: String,
//)
//
//@Entity
//data class QuadrantNote(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    val title: String,
//    val keyword1: String,
//    val content1: String,
//    val keyword2: String,
//    val content2: String,
//    val keyword3: String,
//    val content3: String,
//    val keyword4: String,
//    val content4: String,
//)
//
//@Entity
//data class LadderNote(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    val title: String,
//    val content1: String,
//    val content2: String,
//    val content3: String,
//    val content4: String,
//    val content5: String,
//)
//@Entity
//data class Note(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    val noteType: Int = 0, // Default to 0 for unknown type
//    val title: String,
//    val timestamp: Long = System.currentTimeMillis(),
//) {
//    companion object {
//        const val TYPE_BASIC = 1
//        const val TYPE_CORNELL = 2
//        const val TYPE_OUTLINE = 3
//        const val TYPE_CHARTING = 4
//        const val TYPE_QUADRANT = 5
//        const val TYPE_LADDER = 6
//    }
//}
//
////basic note
//@Entity(
//    foreignKeys = [ForeignKey(entity = Note::class,
//        parentColumns = ["id"],
//        childColumns = ["noteId"])])
//data class Type1Note(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    val noteId: Int,
//    val content: String
//)
//
////cornell note
//@Entity(
//    foreignKeys = [ForeignKey(entity = Note::class,
//        parentColumns = ["id"],
//        childColumns = ["noteId"])])
//data class Type2Note(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    val noteId: Int,
//    val keyword1: String,
//    val content1: String,
//    val keyword2: String,
//    val content2: String,
//    val summary: String,
//)
//
////outline note
////make a main title
//@Entity(
//    foreignKeys = [ForeignKey(entity = Note::class,
//        parentColumns = ["id"],
//        childColumns = ["noteId"])])
//data class Type3Note(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    val noteId: Int,
//    val keyword1: String,
//    val content1: String,
//    val keyword2: String,
//    val content2: String,
//)
//
////charting note
//@Entity(
//    foreignKeys = [ForeignKey(entity = Note::class,
//    parentColumns = ["id"],
//    childColumns = ["noteId"])])
//data class Type4Note(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    val noteId: Int,
//)
//
////quadrant note
////make a main title
//@Entity(
//    foreignKeys = [ForeignKey(entity = Note::class,
//        parentColumns = ["id"],
//        childColumns = ["noteId"])])
//data class Type5Note(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    val noteId: Int,
//    val keyword1: String,
//    val content1: String,
//    val keyword2: String,
//    val content2: String,
//    val keyword3: String,
//    val content3: String,
//    val keyword4: String,
//    val content4: String,
//)
//
////ladder note
//@Entity(
// foreignKeys = [ForeignKey(entity = Note::class,
// parentColumns = ["id"],
// childColumns = ["noteId"])])
//data class Type6Note(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    val noteId: Int,
//    val content1: String,
//    val content2: String,
//    val content3: String,
//    val content4: String,
//    val content5: String,
//)
//
//class InvalidNoteException(message: String): Exception(message)