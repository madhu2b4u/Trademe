package com.org.trademe.listings.data.repository

import com.org.trademe.core.exception.NoDataException
import com.org.trademe.core.network.Result
import com.org.trademe.listings.data.model.ListResponse
import com.org.trademe.listings.data.source.LatestListingsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LatestListingsRepositoryImpl @Inject constructor(
    private val remoteDataSource: LatestListingsRemoteDataSource,
) : LatestListingsRepository {

    override suspend fun getLatestListings(): Flow<Result<ListResponse>> = flow {
        emit(Result.loading())
        try {
            val listResponse = remoteDataSource.getLatestListings()
            if (listResponse.List.isEmpty()) {
                emit(Result.empty("No List", "There are no listings available at the moment."))
                return@flow
            }
            emit(Result.success(listResponse))
        } catch (e: IOException) {
            emit(Result.error("Network error: ${e.message}", null))
        } catch (e: NoDataException) {
            emit(Result.error(e.message ?: "No data found", null))
        } catch (e: Exception) {
            emit(Result.error(e.message ?: "Unknown error", null))
        }
    }
}