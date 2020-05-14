package com.example.yetanothertestapp.dagger

import android.app.Application
import com.example.data.repository.MoviesDataRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplicationContext() = application.applicationContext

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesDataRepository: MoviesDataRepository) = moviesDataRepository
}