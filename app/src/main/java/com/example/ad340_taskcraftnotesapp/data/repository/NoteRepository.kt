package com.example.ad340_taskcraftnotesapp.data.repository


import com.example.ad340_taskcraftnotesapp.data.local.Note
import com.example.ad340_taskcraftnotesapp.data.local.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val dao: NoteDao
) {

    val notes: Flow<List<Note>> = dao.getAllNotes()

    suspend fun insert(note: Note) {
        dao.insert(note)
    }

    suspend fun delete(note: Note) {
        dao.delete(note)
    }
}