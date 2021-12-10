package com.github.aayman93.notes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aayman93.notes.data.models.Note
import com.github.aayman93.notes.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    val sortedByTitle: LiveData<List<Note>> = repository.getNotesSortedByTitle()
    val sortedByDateCreated: LiveData<List<Note>> = repository.getNotesSortedByDateCreated()
    val sortedByDateModified: LiveData<List<Note>> = repository.getNotesSortedByDateModified()

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }
}