package com.example.yetanothertestapp.extensions

import androidx.lifecycle.*

fun <T: ViewModel> provideViewModel(owner: ViewModelStoreOwner, viewModel: Class<T>) =
    ViewModelProvider(owner).get(viewModel)