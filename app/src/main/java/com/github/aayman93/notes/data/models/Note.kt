package com.github.aayman93.notes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var text: String,
    var dateCreated: Long,
    var dateModified: Long
)
