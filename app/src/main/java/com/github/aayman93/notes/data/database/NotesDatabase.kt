package com.github.aayman93.notes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.aayman93.notes.data.models.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
}