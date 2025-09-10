package com.org.trademe.listings.domain

import com.org.trademe.listings.data.model.Listing

interface MockLatestListingsUseCase {
    suspend fun getList(): Result<List<Listing>>
}