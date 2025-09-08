package com.org.trademe.listings.di

import com.org.trademe.listings.data.repository.ListingsRepository
import com.org.trademe.listings.data.repository.MockListingsRepositoryImpl
import com.org.trademe.listings.domain.LatestListingsUseCase
import com.org.trademe.listings.domain.LatestListingsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindRepository(
        listingsRepositoryImpl: MockListingsRepositoryImpl
    ): ListingsRepository

    @Binds
    internal abstract fun bindUseCase(
        useCaseImpl: LatestListingsUseCaseImpl
    ): LatestListingsUseCase
}