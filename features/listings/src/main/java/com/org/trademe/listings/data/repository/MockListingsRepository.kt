package com.org.trademe.listings.data.repository

import com.org.trademe.listings.data.model.Listing

interface MockListingsRepository {
    suspend fun getLatestListings(): Result<List<Listing>>
}