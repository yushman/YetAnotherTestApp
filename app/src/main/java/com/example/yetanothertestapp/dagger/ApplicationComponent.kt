package com.example.yetanothertestapp.dagger

import com.example.yetanothertestapp.ui.fragment.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(baseFragment: BaseFragment)

}