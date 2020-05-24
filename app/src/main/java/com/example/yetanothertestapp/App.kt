package com.example.yetanothertestapp

import android.app.Application
import com.example.yetanothertestapp.koin.KoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(KoinModule.koinModule)
        }
        Timber.plant(Timber.DebugTree())
    }
}