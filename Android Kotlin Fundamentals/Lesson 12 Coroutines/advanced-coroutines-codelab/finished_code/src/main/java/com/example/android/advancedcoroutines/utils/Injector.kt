package com.example.android.advancedcoroutines.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.android.advancedcoroutines.NetworkService
import com.example.android.advancedcoroutines.ui.PlantListViewModelFactory
import com.example.android.advancedcoroutines.PlantRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
interface ViewModelFactoryProvider {
    fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory
}

@FlowPreview
val Injector: ViewModelFactoryProvider
    get() = currentInjector

@FlowPreview
@ExperimentalCoroutinesApi
private object DefaultViewModelProvider: ViewModelFactoryProvider {
    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(
            plantDao(context),
            plantService()
        )
    }

    private fun plantService() = NetworkService()

    private fun plantDao(context: Context) =
        AppDatabase.getInstance(context.applicationContext).plantDao()

    override fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
        val repository = getPlantRepository(context)
        return PlantListViewModelFactory(repository)
    }
}

private object Lock

@FlowPreview
@Volatile private var currentInjector: ViewModelFactoryProvider =
    DefaultViewModelProvider


@VisibleForTesting
private fun setInjectorForTesting(injector: ViewModelFactoryProvider?) {
    synchronized(Lock) {
        currentInjector = injector ?: DefaultViewModelProvider
    }
}

@VisibleForTesting
private fun resetInjector() =
    setInjectorForTesting(null)