package com.vstankevich.lastfm

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by victor.stankevich on 11.07.2018.
 */
object FragmentsRouter {

    fun to(fragmentManager: FragmentManager, fragment: Fragment, tag: String? = null, @IdRes container: Int = R.id.container) {
        val transaction = fragmentManager.beginTransaction()
        if (!tag.isNullOrEmpty()) {
            transaction.addToBackStack(tag)
        }
        transaction.replace(container, fragment, tag).commitAllowingStateLoss()
    }
}