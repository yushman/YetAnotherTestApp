package com.example.yetanothertestapp.dagger

import android.app.Application
import com.example.yetanothertestapp.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, DomainModule::class, AndroidSupportInjectionModule::class])
//
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: App)

//    @Component.Factory
//    interface Factory {
//        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
//    }

}