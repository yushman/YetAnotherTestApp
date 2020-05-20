package com.example.yetanothertestapp

import android.app.Application
import com.example.yetanothertestapp.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)

        Timber.plant(Timber.DebugTree())
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        return DaggerApplicationComponent.factory().create(applicationContext)
//    }

//    override fun androidInjector(): AndroidInjector<Any> = androidInjector


}