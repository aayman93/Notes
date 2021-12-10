package com.github.aayman93.notes.repository

import androidx.lifecycle.LiveData
import com.github.aayman93.notes.data.database.NotesDao
import com.github.aayman93.notes.data.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val notesDao: NotesDao
) {

    fun getNotesSortedByTitle(): LiveData<List<Note>> {
        return notesDao.getNotesSortedByTitle()
    }

    fun getNotesSortedByDateCreated(): LiveData<List<Note>> {
        return notesDao.getNotesSortedByDateCreated()
    }

    fun getNotesSortedByDateModified(): LiveData<List<Note>> {
        return notesDao.getNotesSortedByDateModified()
    }

    suspend fun addNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.addNote(note)
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.updateNote(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.deleteNote(note)
        }
    }

    suspend fun deleteAllNotes() {
        withContext(Dispatchers.IO) {
            notesDao.deleteAllNotes()
        }
    }
}