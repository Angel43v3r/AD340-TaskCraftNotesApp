package com.example.ad340_taskcraftnotesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ad340_taskcraftnotesapp.data.local.Note
import com.example.ad340_taskcraftnotesapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class UiEvent {
    data class ShowError(val message: String) : UiEvent()
    data object NoteSaved : UiEvent()
}

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> =
        repository.notes.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun insertNote(title: String, content: String) {
        if (title.isBlank() || content.isBlank()) {
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.ShowError("Title and content cannot be empty"))
            }
            return
        }

        viewModelScope.launch {
            repository.insert(Note(title = title, content = content))
            _uiEvent.emit(UiEvent.NoteSaved)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    class Factory(private val repository: NoteRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
