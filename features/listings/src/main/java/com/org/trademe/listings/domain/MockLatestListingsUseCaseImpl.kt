package com.org.trademe.listings.domain

import com.org.trademe.listings.data.model.Listing
import com.org.trademe.listings.data.repository.MockListingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockLatestListingsUseCaseImpl @Inject constructor(
    private val repository: MockListingsRepository
) : MockLatestListingsUseCase {
    override suspend fun getList(): Result<List<Listing>> {
        return repository.getLatestListings()
    }
}