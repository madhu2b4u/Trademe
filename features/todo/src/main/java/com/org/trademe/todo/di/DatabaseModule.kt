package com.org.trademe.todo.di

import android.content.Context
import androidx.room.Room
import com.org.trademe.todo.data.local.TodoDao
import com.org.trademe.todo.data.local.TodoDatabase
import com.org.trademe.todo.data.source.TodoLocalDataSource
import com.org.trademe.todo.data.source.TodoLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing database-related dependencies.
 * Provides:
 * - TodoDatabase instance
 * - TodoDao from the database
 * - TodoLocalDataSource implementation
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides the TodoDatabase instance.
     * Using fallbackToDestructiveMigration for development.
     * In production, proper migration strategies should be implemented.
     */
    @Provides
    @Singleton
    fun provideTodoDatabase(
        @ApplicationContext context: Context
    ): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides TodoDao from the TodoDatabase
     */
    @Provides
    @Singleton
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }

    /**
     * Provides TodoLocalDataSource implementation
     */
    @Provides
    @Singleton
    fun provideTodoLocalDataSource(
        todoDao: TodoDao
    ): TodoLocalDataSource {
        return TodoLocalDataSourceImpl(todoDao)
    }
}

