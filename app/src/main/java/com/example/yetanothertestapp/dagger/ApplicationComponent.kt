package com.example.yetanothertestapp.dagger

import android.app.Application
import com.example.yetanothertestapp.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, DomainModule::class, ViewModelModule::class, AndroidSupportInjectionModule::class])
interface ApplicationComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}