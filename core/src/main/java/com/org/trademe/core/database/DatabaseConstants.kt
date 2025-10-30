package com.org.trademe.core.database

/**
 * Shared database constants and utilities for all feature modules.
 * 
 * Architecture:
 * - Core module provides shared utilities and Room dependencies
 * - Each feature module creates its own Room database with feature-specific entities
 * - Feature modules are responsible for their own DAOs and data sources
 * 
 * This approach ensures:
 * - No circular dependencies between core and feature modules
 * - Clean separation of concerns
 * - Each feature can evolve independently
 */
object DatabaseConstants {
    /**
     * Default database configuration
     */
    const val DEFAULT_DATABASE_VERSION = 1
    
    /**
     * Common database operations timeout
     */
    const val DATABASE_OPERATION_TIMEOUT_MS = 5000L
}

