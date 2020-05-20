package com.example.yetanothertestapp.dagger

import com.example.yetanothertestapp.ui.fragment.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun bindFragment(): MoviesFragment
}