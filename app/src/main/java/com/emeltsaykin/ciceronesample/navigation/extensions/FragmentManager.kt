package com.emeltsaykin.ciceronesample.navigation.extensions

import androidx.fragment.app.FragmentManager

internal fun FragmentManager.isBackStackNotEmpty(): Boolean {
    return !this.isBackStackEmpty()
}

internal fun FragmentManager.isBackStackEmpty(): Boolean {
    return this.backStackEntryCount == 0
}