package com.org.trademe.todo.data.repository

import com.org.trademe.core.network.Result
import com.org.trademe.todo.data.mapper.toDomainModel
import com.org.trademe.todo.data.mapper.toDomainModels
import com.org.trademe.todo.data.mapper.toEntity
import com.org.trademe.todo.data.model.Todo
import com.org.trademe.todo.data.source.TodoLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of TodoRepository using Room database for local persistence.
 * This repository follows clean architecture by:
 * - Using local data source for database operations
 * - Converting between domain models and database entities using mappers
 * - Wrapping results in Result sealed class for error handling
 * - Providing reactive data updates using Flow
 */
@Singleton
class TodoRepositoryImpl @Inject constructor(
    private val localDataSource: TodoLocalDataSource
) : TodoRepository {

    override suspend fun getTodos(): Flow<Result<List<Todo>>> {
        return localDataSource.getAllTodos()
            .map { entities ->
                Result.Success(entities.toDomainModels()) as Result<List<Todo>>
            }
            .onStart {
                emit(Result.Loading)
            }
            .catch { exception ->
                emit(Result.Error(exception.message ?: "Unknown error occurred"))
            }
    }

    override suspend fun addTodo(todo: Todo): Result<Todo> {
        return try {
            localDataSource.insertTodo(todo.toEntity())
            Result.Success(todo)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to add todo")
        }
    }

    override suspend fun updateTodo(todo: Todo): Result<Todo> {
        return try {
            localDataSource.updateTodo(todo.toEntity())
            Result.Success(todo)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to update todo")
        }
    }

    override suspend fun deleteTodo(id: String): Result<Unit> {
        return try {
            localDataSource.deleteTodoById(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to delete todo")
        }
    }

    override suspend fun toggleTodoCompletion(id: String): Result<Todo> {
        return try {
            val todoEntity = localDataSource.getTodoById(id)
            if (todoEntity != null) {
                val updatedEntity = todoEntity.copy(isCompleted = !todoEntity.isCompleted)
                localDataSource.updateTodo(updatedEntity)
                Result.Success(updatedEntity.toDomainModel())
            } else {
                Result.Error("Todo not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to toggle todo")
        }
    }
}
