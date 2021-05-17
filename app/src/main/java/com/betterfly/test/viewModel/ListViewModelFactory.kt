package com.betterfly.test.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.betterfly.test.domain.ItemUseCase
///ViewModel provider para cargar  la instancia
class ListViewModelFactory(val reportItemUseCase: ItemUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ItemUseCase::class.java).newInstance(reportItemUseCase)
    }
}