package com.betterfly.test.ui.home.adapter

import android.content.Context
import com.betterfly.test.ui.model.Item
///Interfaz  de funciones para el Listener Onclick
interface OnItemClickListenerItemAdapterRecycler {

    fun LongClick(data_local: Item, position: Int, ctx: Context)
    fun OnClick(data_local: Item, position: Int, ctx: Context)
}
