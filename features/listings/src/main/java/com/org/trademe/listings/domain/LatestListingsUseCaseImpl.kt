package com.org.trademe.listings.domain

import com.org.trademe.listings.data.model.Listing
import com.org.trademe.listings.data.repository.ListingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LatestListingsUseCaseImpl @Inject constructor(
    private val repository: ListingsRepository
) : LatestListingsUseCase {
    override suspend fun getList(): Result<List<Listing>> {
        return repository.getLatestListings()
    }
}