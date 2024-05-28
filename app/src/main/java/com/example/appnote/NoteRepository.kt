package com.example.appnote

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class NoteRepository(context: Context) {
    private val dbHelper = NoteDatabaseHelper(context)

    fun addNote(note: Note): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(NoteDatabaseHelper.COLUMN_TITLE, note.title)
            put(NoteDatabaseHelper.COLUMN_CONTENT, note.content)
        }
        return db.insert(NoteDatabaseHelper.TABLE_NAME, null, values)
    }

    fun getAllNotes(): List<Note> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(NoteDatabaseHelper.TABLE_NAME, null, null, null, null, null, null)
        val notes = mutableListOf<Note>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_TITLE))
                val content = getString(getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_CONTENT))
                notes.add(Note(id, title, content))
            }
        }
        cursor.close()
        return notes
    }

    // Métodos para actualizar y eliminar notas
}
