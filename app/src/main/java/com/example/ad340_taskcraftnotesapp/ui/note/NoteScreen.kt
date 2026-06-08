package com.example.ad340_taskcraftnotesapp.ui.note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ad340_taskcraftnotesapp.viewmodel.NoteViewModel
import com.example.ad340_taskcraftnotesapp.viewmodel.UiEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(viewModel: NoteViewModel) {

    val notes by viewModel.notes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
                is UiEvent.NoteSaved -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "TaskCraft Notes",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->

        if (notes.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "No notes yet",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Tap the + button in the bottom right\nto add your first note.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(notes, key = { it.id }) { note ->
                    SwipeToDeleteContainer(
                        onDelete = { viewModel.deleteNote(note) }
                    ) {
                        NoteItem(
                            note = note,
                            onDelete = { viewModel.deleteNote(note) }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddNoteDialog(
            onDismiss = { showDialog = false },
            onSave = { title, content ->
                viewModel.insertNote(title, content)
                if (title.isNotBlank() && content.isNotBlank()) {
                    showDialog = false
                }
            }
        )
    }
}