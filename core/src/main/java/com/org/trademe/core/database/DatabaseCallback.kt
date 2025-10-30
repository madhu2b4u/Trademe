package com.org.trademe.core.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Base database callback that can be extended by feature modules.
 * Provides common lifecycle methods for database creation and opening.
 */
abstract class DatabaseCallback : RoomDatabase.Callback() {
    
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        onDatabaseCreated(db)
    }
    
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        onDatabaseOpened(db)
    }
    
    /**
     * Called when the database is created for the first time.
     * Override this to perform initial setup or seed data.
     */
    open fun onDatabaseCreated(db: SupportSQLiteDatabase) {
        // Override in feature modules if needed
    }
    
    /**
     * Called when the database is opened.
     * Override this to perform checks or initialization.
     */
    open fun onDatabaseOpened(db: SupportSQLiteDatabase) {
        // Override in feature modules if needed
    }
}

