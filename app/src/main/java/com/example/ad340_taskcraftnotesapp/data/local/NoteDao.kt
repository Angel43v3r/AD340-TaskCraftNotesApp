package com.example.ad340_taskcraftnotesapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
}