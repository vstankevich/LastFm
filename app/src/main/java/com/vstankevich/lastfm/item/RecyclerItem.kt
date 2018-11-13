package com.vstankevich.lastfm

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View

interface RecyclerItem {

    @LayoutRes fun getLayoutId(): Int

    fun renderView(context: Context, view: View)

    fun initView(context: Context, view: View)
}