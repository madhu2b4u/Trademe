package com.org.trademe.todo.domain

import com.org.trademe.core.network.Result
import com.org.trademe.todo.data.model.Todo
import kotlinx.coroutines.flow.Flow

/**
 * Use case interface for Todo operations
 * Encapsulates business logic and acts as a bridge between presentation and data layers
 */
interface TodoUseCase {
    /**
     * Get all todos
     */
    suspend fun getTodos(): Flow<Result<List<Todo>>>

    /**
     * Add a new todo
     */
    suspend fun addTodo(title: String, description: String): Result<Todo>

    /**
     * Update an existing todo
     */
    suspend fun updateTodo(todo: Todo): Result<Todo>

    /**
     * Delete a todo
     */
    suspend fun deleteTodo(id: String): Result<Unit>

    /**
     * Toggle todo completion status
     */
    suspend fun toggleTodoCompletion(id: String): Result<Todo>
}

