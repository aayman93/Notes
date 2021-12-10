package com.github.aayman93.notes.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var text: String,
    var dateCreated: Long,
    var dateModified: Long
) : Parcelable
