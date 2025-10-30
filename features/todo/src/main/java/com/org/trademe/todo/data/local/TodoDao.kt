package com.org.trademe.todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for Todo operations.
 * Provides methods to interact with the todos table in the database.
 */
@Dao
interface TodoDao {
    
    /**
     * Get all todos as a Flow for reactive updates.
     * The Flow will emit new values whenever the data changes.
     */
    @Query("SELECT * FROM todos ORDER BY created_at DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>
    
    /**
     * Get a single todo by id
     */
    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: String): TodoEntity?
    
    /**
     * Insert a new todo. Replace if conflict occurs.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)
    
    /**
     * Insert multiple todos
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todos: List<TodoEntity>)
    
    /**
     * Update an existing todo
     */
    @Update
    suspend fun updateTodo(todo: TodoEntity)
    
    /**
     * Delete a todo
     */
    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
    
    /**
     * Delete a todo by id
     */
    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteTodoById(id: String)
    
    /**
     * Delete all todos (useful for testing)
     */
    @Query("DELETE FROM todos")
    suspend fun deleteAllTodos()
    
    /**
     * Get count of completed todos
     */
    @Query("SELECT COUNT(*) FROM todos WHERE is_completed = 1")
    suspend fun getCompletedTodosCount(): Int
    
    /**
     * Get count of pending todos
     */
    @Query("SELECT COUNT(*) FROM todos WHERE is_completed = 0")
    suspend fun getPendingTodosCount(): Int
}

