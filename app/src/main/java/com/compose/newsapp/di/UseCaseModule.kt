package com.compose.newsapp.di

import com.compose.newsapp.feature.home.domain.repository.HomeRepository
import com.compose.newsapp.feature.home.domain.usecase.LatestNewsUseCase
import com.compose.newsapp.feature.home.domain.usecase.NewsTopicUseCase
import com.compose.newsapp.feature.search.domain.repository.SearchRepository
import com.compose.newsapp.feature.search.domain.usecase.SearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideLatestNewsUseCase(repository: HomeRepository): LatestNewsUseCase {
        return LatestNewsUseCase(repository)
    }

    @Provides
    fun provideNewsTopicUseCase(repository: HomeRepository): NewsTopicUseCase {
        return NewsTopicUseCase(repository)
    }

    @Provides
    fun provideSearchNewsUseCase(repository: SearchRepository): SearchNewsUseCase {
        return SearchNewsUseCase(repository)
    }

}