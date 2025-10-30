package com.org.trademe.todo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room database entity for Todo items.
 * Represents the database table structure for storing todos locally.
 */
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    
    @ColumnInfo(name = "title")
    val title: String,
    
    @ColumnInfo(name = "description")
    val description: String,
    
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long
)

