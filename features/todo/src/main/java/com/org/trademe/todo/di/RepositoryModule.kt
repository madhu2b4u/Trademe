package com.org.trademe.todo.di

import com.org.trademe.todo.data.repository.TodoRepository
import com.org.trademe.todo.data.repository.TodoRepositoryImpl
import com.org.trademe.todo.domain.TodoUseCase
import com.org.trademe.todo.domain.TodoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing repository and use case dependencies.
 * Uses @Binds for efficient dependency injection without creating wrapper functions.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds TodoRepositoryImpl to TodoRepository interface
     */
    @Binds
    @Singleton
    internal abstract fun bindTodoRepository(
        repositoryImpl: TodoRepositoryImpl
    ): TodoRepository

    /**
     * Binds TodoUseCaseImpl to TodoUseCase interface
     */
    @Binds
    @Singleton
    internal abstract fun bindTodoUseCase(
        useCaseImpl: TodoUseCaseImpl
    ): TodoUseCase
}

