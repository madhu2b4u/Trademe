package com.org.trademe.todo.di

import com.org.trademe.navigation.NavigationProvider
import com.org.trademe.todo.TodoNavigationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing navigation dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class TodoNavigationModule {

    @Binds
    @IntoSet
    @Singleton
    abstract fun bindTodoNavigationProvider(
        provider: TodoNavigationProvider
    ): NavigationProvider
}

