package com.org.trademe.todo.data.mapper

import com.org.trademe.todo.data.local.TodoEntity
import com.org.trademe.todo.data.model.Todo

/**
 * Mapper functions to convert between domain models and database entities.
 * Follows the clean architecture principle of keeping layers independent.
 */

/**
 * Convert TodoEntity (database) to Todo (domain model)
 */
fun TodoEntity.toDomainModel(): Todo {
    return Todo(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        createdAt = this.createdAt
    )
}

/**
 * Convert Todo (domain model) to TodoEntity (database)
 */
fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        createdAt = this.createdAt
    )
}

/**
 * Convert list of TodoEntity to list of Todo
 */
fun List<TodoEntity>.toDomainModels(): List<Todo> {
    return this.map { it.toDomainModel() }
}

/**
 * Convert list of Todo to list of TodoEntity
 */
fun List<Todo>.toEntities(): List<TodoEntity> {
    return this.map { it.toEntity() }
}

