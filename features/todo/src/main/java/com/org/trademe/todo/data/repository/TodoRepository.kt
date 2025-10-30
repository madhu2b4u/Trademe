package com.org.trademe.todo.data.repository

import com.org.trademe.core.network.Result
import com.org.trademe.todo.data.model.Todo
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Todo operations
 * Follows clean architecture by defining a contract that can be implemented by different data sources
 */
interface TodoRepository {
    /**
     * Get all todos as a Flow for reactive updates
     */
    suspend fun getTodos(): Flow<Result<List<Todo>>>

    /**
     * Add a new todo item
     */
    suspend fun addTodo(todo: Todo): Result<Todo>

    /**
     * Update an existing todo item
     */
    suspend fun updateTodo(todo: Todo): Result<Todo>

    /**
     * Delete a todo item by id
     */
    suspend fun deleteTodo(id: String): Result<Unit>

    /**
     * Toggle the completion status of a todo
     */
    suspend fun toggleTodoCompletion(id: String): Result<Todo>
}

