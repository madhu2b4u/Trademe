package com.org.trademe.core.network

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Provides a Gson instance for JSON serialization/deserialization throughout the app.
 *
 * This module is responsible for:
 * - Converting JSON responses from the TradeMe API into Kotlin objects
 * - Handling the transformation between JSON field names and Kotlin properties
 * - Supporting Retrofit as a converter factory for parsing API responses
 * - Maintaining a single source of truth for JSON parsing configuration
 *
 * The Gson instance is provided as a singleton to ensure consistent JSON handling
 * across all components that require JSON parsing.
 */

@Module
@InstallIn(SingletonComponent::class)
object GsonModule {
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}