package com.example.yetanothertestapp.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.FetchMoviesUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import com.example.yetanothertestapp.ui.fragment.MoviesFragment
import com.example.yetanothertestapp.ui.fragment.MoviesFragmentViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module(includes = [FragmentBindingModule.ProvideViewModel::class])
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(
        modules = [
            InjectViewModel::class
        ]
    )
    abstract fun bind(): MoviesFragment


    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(MoviesFragmentViewModel::class)
        fun provideViewModel(
            fetchMoviesUseCase: FetchMoviesUseCase,
            getFavoritesUseCase: GetFavoritesUseCase
        ): ViewModel = MoviesFragmentViewModel(fetchMoviesUseCase, getFavoritesUseCase)

        @Provides
        fun provideViewModelFactory(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory =
            AppViewModelFactory(creators)

        @Provides
        fun provideFetchMoviesUseCase(repo: MoviesRepository) = FetchMoviesUseCase(repo)

        @Provides
        fun provideGetFavoritesUseCase(repo: MoviesRepository) = GetFavoritesUseCase(repo)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideMoviesFragmentViewModel(
            factory: ViewModelProvider.Factory,
            target: MoviesFragment
        ) = ViewModelProvider(target, factory).get(MoviesFragmentViewModel::class.java)
    }

//    @Binds
//    @IntoMap
//    @ClassKey(MoviesFragment::class)
//    abstract fun bindMoviesFragment(factory: FragmentSubComponent.Factory): AndroidInjector.Factory<*>
}