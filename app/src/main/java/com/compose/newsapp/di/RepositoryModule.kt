package com.compose.newsapp.di

import com.compose.newsapp.feature.home.data.repository.HomeRepositoryImpl
import com.compose.newsapp.feature.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepositoryImpl(repository: HomeRepositoryImpl): HomeRepository
}