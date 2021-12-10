package com.github.aayman93.notes.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.aayman93.notes.data.models.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY LOWER(title)")
    fun getNotesSortedByTitle(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY dateCreated")
    fun getNotesSortedByDateCreated(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY dateModified")
    fun getNotesSortedByDateModified(): LiveData<List<Note>>

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}