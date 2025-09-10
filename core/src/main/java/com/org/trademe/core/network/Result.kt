package com.org.trademe.core.network

/**
 * A generic wrapper class that handles API responses and other asynchronous operations.
 * Represents different states of data loading: Success, Empty, Error, and Loading.
 *
 * @param T The type of data being wrapped
 */
sealed class Result<out T> {

    /**
     * Represents a successful operation with returned data
     * @property data The data returned from the operation
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Represents an operation that completed successfully but returned no data
     * @property title A title describing the empty state
     * @property message A detailed message explaining the empty state
     */
    data class Empty<out T>(val title: String, val message: String) : Result<T>()

    /**
     * Represents a failed operation
     * @property message Description of the error
     * @property data Optional data that might be available despite the error
     */
    data class Error<out T>(val message: String, val data: T? = null) : Result<T>()

    /**
     * Represents an ongoing operation
     */
    data object Loading : Result<Nothing>()

    companion object {
        /**
         * Creates a Success result with the given data
         * @param data The data to be wrapped
         */
        fun <T> success(data: T): Result<T> = Success(data)

        /**
         * Creates an Empty result with title and message
         * @param title Title describing the empty state
         * @param message Detailed message explaining the empty state
         */
        fun <T> empty(title: String, message: String): Result<T> = Empty(title, message)

        /**
         * Creates an Error result with optional data
         * @param msg Description of the error
         * @param data Optional data that might be available despite the error
         */
        fun <T> error(msg: String, data: T? = null): Result<T> = Error(msg, data)

        /**
         * Creates a Loading result
         */
        fun <T> loading(): Result<T> = Loading
    }
}