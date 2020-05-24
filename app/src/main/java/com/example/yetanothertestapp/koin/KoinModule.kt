package com.example.yetanothertestapp.koin

import androidx.room.Room
import com.example.data.local.database.FavoriteMoviesDatabase
import com.example.data.mapper.FavoriteMapper
import com.example.data.mapper.RemoteMapper
import com.example.data.remote.RemoteApi
import com.example.data.remote.services.MoviesApiService
import com.example.data.repository.MoviesDataRepository
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.FetchMoviesUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import com.example.yetanothertestapp.ui.fragment.MoviesFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val koinModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                FavoriteMoviesDatabase::class.java,
                "YATA_database"
            ).build()
        }
        single { get<FavoriteMoviesDatabase>().favoriteMoviesDao() }
        single { RemoteApi() }
        single { get<RemoteApi>().createService(MoviesApiService::class.java) }
        factory { FavoriteMapper() }
        factory { RemoteMapper() }
        single<MoviesRepository> { MoviesDataRepository(get(), get(), get(), get()) }
        single { FetchMoviesUseCase(get()) }
        single { GetFavoritesUseCase(get()) }
        viewModel { MoviesFragmentViewModel(get(), get()) }
    }
}