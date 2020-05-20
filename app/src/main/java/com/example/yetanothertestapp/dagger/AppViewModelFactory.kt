package com.example.yetanothertestapp.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class AppViewModelFactory(
    private val creators: Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return provider.get() as T
//    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        requireNotNull(getProvider(modelClass).get()) {
            "Provider for $modelClass returned null"
        }

    @Suppress("UNCHECKED_CAST")
    private fun <T : ViewModel> getProvider(modelClass: Class<T>): Provider<T> =
        try {
            requireNotNull(creators[modelClass] as Provider<T>) {
                "No ViewModel provider is bound for class $modelClass"
            }
        } catch (cce: ClassCastException) {
            error("Wrong provider type registered for ViewModel type $modelClass")
        }


//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        var creator: Provider<out ViewModel>? = creators[modelClass]
//
//        if (creator == null) {
//            for ((key, value) in creators) {
//                if (modelClass.isAssignableFrom(key)) {
//                    creator = value
//                    break
//                }
//            }
//        }
//
//        if (creator == null) {
//            throw IllegalArgumentException("unknown model class " + modelClass)
//        }
//
//        try {
//            return creator.get() as T
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
//    }
}