package com.org.trademe.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database for Todo feature.
 * This database is feature-specific and contains only Todo-related entities.
 * 
 * Architecture:
 * - Database class defined in feature module (todo)
 * - Provides feature-specific DAOs
 * - Can be extended to include more entities as the feature grows
 */
@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    
    /**
     * Provides access to TodoDao for database operations
     */
    abstract fun todoDao(): TodoDao
    
    companion object {
        const val DATABASE_NAME = "todo_database"
    }
}

