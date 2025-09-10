package com.org.trademe.listings.domain

import com.org.trademe.listings.data.repository.LatestListingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LatestListingsUseCaseImpl @Inject constructor(
    private val repository: LatestListingsRepository
) : LatestListingsUseCase {

    override suspend fun getLatestListings() = repository.getLatestListings()
}