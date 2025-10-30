package com.org.trademe.todo.data.source

import com.org.trademe.todo.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface for local data source operations.
 * Abstracts the local database operations from the repository.
 */
interface TodoLocalDataSource {
    /**
     * Get all todos as a Flow
     */
    fun getAllTodos(): Flow<List<TodoEntity>>
    
    /**
     * Get a todo by id
     */
    suspend fun getTodoById(id: String): TodoEntity?
    
    /**
     * Insert a new todo
     */
    suspend fun insertTodo(todo: TodoEntity)
    
    /**
     * Insert multiple todos
     */
    suspend fun insertTodos(todos: List<TodoEntity>)
    
    /**
     * Update a todo
     */
    suspend fun updateTodo(todo: TodoEntity)
    
    /**
     * Delete a todo
     */
    suspend fun deleteTodo(todo: TodoEntity)
    
    /**
     * Delete a todo by id
     */
    suspend fun deleteTodoById(id: String)
    
    /**
     * Delete all todos
     */
    suspend fun deleteAllTodos()
}

