package com.example.ad340_taskcraftnotesapp.ui.note

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ad340_taskcraftnotesapp.data.local.Note
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteItem(
    note: Note,
    onDelete: () -> Unit
) {
    val dateFormatter = SimpleDateFormat("MMM d, yyyy  h:mm a", Locale.getDefault())
    val formattedDate = dateFormatter.format(Date(note.createdAt))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
