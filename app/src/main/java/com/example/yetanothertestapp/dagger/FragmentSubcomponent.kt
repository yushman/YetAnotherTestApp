package com.example.yetanothertestapp.dagger

import com.example.yetanothertestapp.ui.fragment.MoviesFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface FragmentSubcomponent : AndroidInjector<MoviesFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MoviesFragment>

}