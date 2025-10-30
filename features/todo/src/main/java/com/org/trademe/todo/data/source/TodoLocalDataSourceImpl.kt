package com.org.trademe.todo.data.source

import com.org.trademe.todo.data.local.TodoDao
import com.org.trademe.todo.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of TodoLocalDataSource using Room database.
 * This class serves as a bridge between the repository and the Room DAO.
 */
@Singleton
class TodoLocalDataSourceImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoLocalDataSource {
    
    override fun getAllTodos(): Flow<List<TodoEntity>> {
        return todoDao.getAllTodos()
    }
    
    override suspend fun getTodoById(id: String): TodoEntity? {
        return todoDao.getTodoById(id)
    }
    
    override suspend fun insertTodo(todo: TodoEntity) {
        todoDao.insertTodo(todo)
    }
    
    override suspend fun insertTodos(todos: List<TodoEntity>) {
        todoDao.insertTodos(todos)
    }
    
    override suspend fun updateTodo(todo: TodoEntity) {
        todoDao.updateTodo(todo)
    }
    
    override suspend fun deleteTodo(todo: TodoEntity) {
        todoDao.deleteTodo(todo)
    }
    
    override suspend fun deleteTodoById(id: String) {
        todoDao.deleteTodoById(id)
    }
    
    override suspend fun deleteAllTodos() {
        todoDao.deleteAllTodos()
    }
}

