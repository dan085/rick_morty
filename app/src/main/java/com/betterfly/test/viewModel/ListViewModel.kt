package com.betterfly.test.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.betterfly.test.ui.model.Item
import com.betterfly.test.domain.ItemUseCase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ListViewModel(val itemUseCase:ItemUseCase): ViewModel()  {
    ///Lista que puede ser mutable
    private val listData = MutableLiveData<ArrayList<Item>>()

    //para enviar datos nuevos al observador
    fun setListData(listItems:ArrayList<Item>){
        listData.postValue(listItems)
    }

    //Podemos usar corutinas para pedir informacion que sea asyncrona
    fun getListItems(ctx:Context,Page:Int){
        val service: ExecutorService = Executors.newSingleThreadExecutor()
        service.submit({ // background thread, para obtener  la lista de items
             val data = itemUseCase.getListItems(ctx,Page)
              setListData(data)
        })
    }
    fun getListItemsLiveData(): LiveData<ArrayList<Item>> {
        return listData
    }
    fun clear(){
        listData.postValue(ArrayList())
    }
}