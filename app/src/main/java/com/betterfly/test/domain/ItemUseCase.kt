package com.betterfly.test.domain

import android.content.Context
import com.betterfly.test.data.ListDataSet
import com.betterfly.test.ui.model.Item

class ItemUseCase {

    private val ReportItemDataSet = ListDataSet()
    ///Función para obtener la lista de Item de esta forma se tiene un sistema independiente
    // y permitiendo tener sistemas que mas simple modificar a gran escala
     fun getListItems(ctx:Context,Page:Int):ArrayList<Item>{
        return ReportItemDataSet.getListItems(ctx,Page)
    }
}