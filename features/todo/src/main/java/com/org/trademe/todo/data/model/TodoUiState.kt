package com.org.trademe.todo.data.model

/**
 * UI State for the Todo list screen
 */
data class TodoUiState(
    val isLoading: Boolean = false,
    val todos: List<Todo> = emptyList(),
    val error: String? = null
)

