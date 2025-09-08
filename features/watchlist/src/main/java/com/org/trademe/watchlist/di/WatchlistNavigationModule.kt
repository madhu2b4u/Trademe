package com.org.trademe.watchlist.di

import com.org.trademe.navigation.NavigationProvider
import com.org.trademe.watchlist.navigation.WatchlistNavigationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WatchlistNavigationModule {

    @Binds
    @IntoSet
    @Singleton
    abstract fun bindWatchlistNavigationProvider(
        provider: WatchlistNavigationProvider
    ): NavigationProvider
}