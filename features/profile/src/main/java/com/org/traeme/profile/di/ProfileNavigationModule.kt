package com.org.traeme.profile.di

import com.org.trademe.navigation.NavigationProvider
import com.org.traeme.profile.navigation.ProfileNavigationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileNavigationModule {

    @Binds
    @IntoSet
    @Singleton
    abstract fun bindProfileNavigationProvider(
        provider: ProfileNavigationProvider
    ): NavigationProvider
}