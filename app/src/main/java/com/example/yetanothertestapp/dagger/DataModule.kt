package com.example.yetanothertestapp.dagger

import androidx.room.Room
import com.example.data.local.database.FavoriteMoviesDatabase
import com.example.data.mapper.FavoriteMapper
import com.example.data.mapper.RemoteMapper
import com.example.data.remote.RemoteApi
import com.example.data.remote.services.MoviesApiService
import com.example.yetanothertestapp.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideFavoriteMapper() = FavoriteMapper()

    @Provides
    fun provideRemoteMapper() = RemoteMapper()

    @Provides
    @Singleton
    fun provideRemoteApi() = RemoteApi()

    @Provides
    @Singleton
    fun provideMoviesApiService(api: RemoteApi) = api.createService(MoviesApiService::class.java)

    @Provides
    @Singleton
    fun provideRoomDatabase(app: App) = Room.databaseBuilder(
        app.applicationContext,
        FavoriteMoviesDatabase::class.java,
        "YATA_database"
    ).build()
}