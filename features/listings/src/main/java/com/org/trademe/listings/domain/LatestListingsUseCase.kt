package com.org.trademe.listings.domain

import com.org.trademe.listings.data.model.Listing

interface LatestListingsUseCase {
    suspend fun getList(): Result<List<Listing>>
}