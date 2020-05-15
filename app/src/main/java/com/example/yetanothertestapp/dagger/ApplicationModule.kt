package com.example.yetanothertestapp.dagger

import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.FetchMoviesUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

//    @Provides
//    @Singleton
//    fun provideApplicationContext() = application

    @Provides
    fun provideFetchMoviesUseCase(repo: MoviesRepository) = FetchMoviesUseCase(repo)

    @Provides
    fun provideGetFavoritesUseCase(repo: MoviesRepository) = GetFavoritesUseCase(repo)
}