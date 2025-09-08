package com.org.trademe.listings.di

import com.org.trademe.listings.ListingsNavigationProvider
import com.org.trademe.navigation.NavigationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ListingsNavigationModule {

    @Binds
    @IntoSet
    @Singleton
    abstract fun bindListingsNavigationProvider(
        provider: ListingsNavigationProvider
    ): NavigationProvider
}