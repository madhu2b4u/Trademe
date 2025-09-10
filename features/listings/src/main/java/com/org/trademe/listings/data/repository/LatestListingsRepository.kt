package com.org.trademe.listings.data.repository

import com.org.trademe.core.network.Result
import com.org.trademe.listings.data.model.ListResponse
import kotlinx.coroutines.flow.Flow

interface LatestListingsRepository {
    suspend fun getLatestListings(): Flow<Result<ListResponse>>
}