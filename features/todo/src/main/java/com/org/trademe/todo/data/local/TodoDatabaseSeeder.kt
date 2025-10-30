package com.org.trademe.todo.data.local

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Database seeder for populating initial Todo data.
 * Useful for development and testing purposes.
 */
@Singleton
class TodoDatabaseSeeder @Inject constructor(
    private val todoDao: TodoDao
) {
    /**
     * Seeds the database with sample todos if it's empty.
     * Call this during app initialization if needed.
     */
    suspend fun seedIfEmpty() {
        // Check if database is empty
        val existingTodos = todoDao.getPendingTodosCount() + todoDao.getCompletedTodosCount()
        
        if (existingTodos == 0) {
            val sampleTodos = listOf(
                TodoEntity(
                    id = "1",
                    title = "Complete MVVM Architecture",
                    description = "Implement clean architecture with MVVM pattern",
                    isCompleted = true,
                    createdAt = System.currentTimeMillis() - 86400000 * 4 // 4 days ago
                ),
                TodoEntity(
                    id = "2",
                    title = "Setup Dependency Injection",
                    description = "Configure Hilt for dependency injection",
                    isCompleted = true,
                    createdAt = System.currentTimeMillis() - 86400000 * 3 // 3 days ago
                ),
                TodoEntity(
                    id = "3",
                    title = "Implement Room Database",
                    description = "Setup Room for local data persistence",
                    isCompleted = true,
                    createdAt = System.currentTimeMillis() - 86400000 * 2 // 2 days ago
                ),
                TodoEntity(
                    id = "4",
                    title = "Create Navigation Module",
                    description = "Setup navigation between features",
                    isCompleted = false,
                    createdAt = System.currentTimeMillis() - 86400000 // 1 day ago
                ),
                TodoEntity(
                    id = "5",
                    title = "Add Unit Tests",
                    description = "Write comprehensive unit tests for all layers",
                    isCompleted = false,
                    createdAt = System.currentTimeMillis()
                )
            )
            
            todoDao.insertTodos(sampleTodos)
        }
    }
}

