package com.example.yetanothertestapp.dagger

import com.example.data.repository.MoviesDataRepository
import com.example.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(repo: MoviesDataRepository): MoviesRepository = repo
}