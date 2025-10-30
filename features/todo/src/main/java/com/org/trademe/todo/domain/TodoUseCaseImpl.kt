package com.org.trademe.todo.domain

import com.org.trademe.core.network.Result
import com.org.trademe.todo.data.model.Todo
import com.org.trademe.todo.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

/**
 * Implementation of TodoUseCase
 * Contains business logic and delegates data operations to repository
 */
class TodoUseCaseImpl @Inject constructor(
    private val repository: TodoRepository
) : TodoUseCase {

    override suspend fun getTodos(): Flow<Result<List<Todo>>> {
        return repository.getTodos()
    }

    override suspend fun addTodo(title: String, description: String): Result<Todo> {
        // Business logic: Validate input
        if (title.isBlank()) {
            return Result.Error("Title cannot be empty")
        }

        val todo = Todo(
            id = UUID.randomUUID().toString(),
            title = title.trim(),
            description = description.trim(),
            isCompleted = false,
            createdAt = System.currentTimeMillis()
        )

        return repository.addTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo): Result<Todo> {
        // Business logic: Validate input
        if (todo.title.isBlank()) {
            return Result.Error("Title cannot be empty")
        }

        return repository.updateTodo(todo)
    }

    override suspend fun deleteTodo(id: String): Result<Unit> {
        return repository.deleteTodo(id)
    }

    override suspend fun toggleTodoCompletion(id: String): Result<Todo> {
        return repository.toggleTodoCompletion(id)
    }
}

