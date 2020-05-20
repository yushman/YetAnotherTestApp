package com.example.yetanothertestapp.dagger

import com.example.yetanothertestapp.ui.activity.MoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module()
abstract class ApplicationModule {

    @ContributesAndroidInjector(modules = [ActivityBindingModule::class])
    abstract fun mainActivityInjector(): MoviesActivity


}