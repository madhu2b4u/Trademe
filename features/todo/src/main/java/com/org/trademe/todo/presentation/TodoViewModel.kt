package com.org.trademe.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.trademe.core.network.Result
import com.org.trademe.todo.data.model.Todo
import com.org.trademe.todo.data.model.TodoUiState
import com.org.trademe.todo.domain.TodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Todo feature
 * Manages UI state and coordinates between UI and domain layer
 */
@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoUseCase: TodoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        loadTodos()
    }

    /**
     * Load all todos
     */
    private fun loadTodos() {
        viewModelScope.launch {
            todoUseCase.getTodos().collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                    }

                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            todos = result.data,
                            error = null
                        )
                    }

                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    is Result.Empty -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            todos = emptyList(),
                            error = null
                        )
                    }
                }
            }
        }
    }

    /**
     * Add a new todo
     */
    fun addTodo(title: String, description: String) {
        viewModelScope.launch {
            when (val result = todoUseCase.addTodo(title, description)) {
                is Result.Success -> {
                    // Reload todos to reflect the change
                    loadTodos()
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(error = result.message)
                }

                else -> {
                    // Handle other cases if needed
                }
            }
        }
    }

    /**
     * Update an existing todo
     */
    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            when (val result = todoUseCase.updateTodo(todo)) {
                is Result.Success -> {
                    loadTodos()
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(error = result.message)
                }

                else -> {
                    // Handle other cases if needed
                }
            }
        }
    }

    /**
     * Delete a todo
     */
    fun deleteTodo(id: String) {
        viewModelScope.launch {
            when (val result = todoUseCase.deleteTodo(id)) {
                is Result.Success -> {
                    loadTodos()
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(error = result.message)
                }

                else -> {
                    // Handle other cases if needed
                }
            }
        }
    }

    /**
     * Toggle todo completion status
     */
    fun toggleTodoCompletion(id: String) {
        viewModelScope.launch {
            when (val result = todoUseCase.toggleTodoCompletion(id)) {
                is Result.Success -> {
                    loadTodos()
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(error = result.message)
                }

                else -> {
                    // Handle other cases if needed
                }
            }
        }
    }

    /**
     * Retry loading todos after an error
     */
    fun retry() {
        loadTodos()
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

