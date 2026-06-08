package com.example.ad340_taskcraftnotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ad340_taskcraftnotesapp.data.local.NoteDatabase
import com.example.ad340_taskcraftnotesapp.data.repository.NoteRepository
import com.example.ad340_taskcraftnotesapp.ui.note.NoteScreen
import com.example.ad340_taskcraftnotesapp.ui.theme.AD340TaskCraftNotesAppTheme
import com.example.ad340_taskcraftnotesapp.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: NoteViewModel by viewModels {
        val dao = NoteDatabase.getDatabase(this).noteDao()
        val repository = NoteRepository(dao)
        NoteViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AD340TaskCraftNotesAppTheme {
                NoteScreen(viewModel)
            }
        }
    }
}
