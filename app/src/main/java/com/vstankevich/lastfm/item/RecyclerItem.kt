package com.vstankevich.lastfm

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes

interface RecyclerItem {

    @LayoutRes fun getLayoutId(): Int

    fun renderView(context: Context, view: View)

    fun initView(context: Context, view: View)
}