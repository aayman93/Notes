package com.github.aayman93.notes.data.database

import androidx.room.Dao
import androidx.room.Insert
import com.github.aayman93.notes.data.models.Note

@Dao
interface NotesDao {

    @Insert
    suspend fun addNote(note: Note)
}