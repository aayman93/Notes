package com.github.aayman93.notes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aayman93.notes.data.models.Note
import com.github.aayman93.notes.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.addNote(note)
        }
    }

    fun validateInputs(noteTitle: String, noteText: String): Boolean {
        return !(noteTitle.isBlank() || noteText.isBlank())
    }
}