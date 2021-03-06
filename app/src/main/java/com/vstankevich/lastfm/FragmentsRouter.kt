package com.vstankevich.lastfm

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by victor.stankevich on 11.07.2018.
 */
object FragmentsRouter {

    fun to(fragmentManager: FragmentManager, fragment: Fragment, @IdRes container: Int = R.id.container) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(container, fragment).commitAllowingStateLoss()
    }
}