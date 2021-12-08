package com.github.aayman93.notes.repository

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

    suspend fun addNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.addNote(note)
        }
    }
}