package dev.olog.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dev.olog.core.extensions.getTopFragment
import timber.log.Timber

fun findFirstVisibleFragment(fragmentManager: FragmentManager): Fragment? {
    var topFragment = fragmentManager.getTopFragment()
    if (topFragment == null) {
        topFragment = fragmentManager.fragments
            .filter { it.isVisible }
            .firstOrNull { BottomNavigatorImpl.fragments.contains(it.tag) }
    }
    if (topFragment == null) {
        Timber.e("Navigator: Something went wrong, for some reason no fragment was found")
    }
    return topFragment
}