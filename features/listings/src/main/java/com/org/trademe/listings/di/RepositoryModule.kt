package com.org.trademe.listings.di

import com.org.trademe.listings.data.repository.LatestListingsRepository
import com.org.trademe.listings.data.repository.LatestListingsRepositoryImpl
import com.org.trademe.listings.data.repository.MockListingsRepository
import com.org.trademe.listings.data.repository.MockListingsRepositoryImpl
import com.org.trademe.listings.domain.LatestListingsUseCase
import com.org.trademe.listings.domain.LatestListingsUseCaseImpl
import com.org.trademe.listings.domain.MockLatestListingsUseCase
import com.org.trademe.listings.domain.MockLatestListingsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindLatestListingRepository(
        listingsRepositoryImpl: LatestListingsRepositoryImpl
    ): LatestListingsRepository

    @Binds
    internal abstract fun bindLatestListingsUseCase(
        useCaseImpl: LatestListingsUseCaseImpl
    ): LatestListingsUseCase

    @Binds
    internal abstract fun bindMockRepository(
        listingsRepositoryImpl: MockListingsRepositoryImpl
    ): MockListingsRepository

    @Binds
    internal abstract fun bindMockUseCase(
        useCaseImpl: MockLatestListingsUseCaseImpl
    ): MockLatestListingsUseCase
}