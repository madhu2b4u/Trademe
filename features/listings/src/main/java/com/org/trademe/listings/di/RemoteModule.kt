package com.org.trademe.listings.di

import com.org.trademe.listings.data.service.ListingsApiService
import com.org.trademe.listings.data.source.LatestListingsRemoteDataSource
import com.org.trademe.listings.data.source.LatestListingsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [RemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: LatestListingsRemoteDataSourceImpl
        ): LatestListingsRemoteDataSource
    }

    @Provides
    fun provideService(retrofit: Retrofit): ListingsApiService =
        retrofit.create(ListingsApiService::class.java)
}