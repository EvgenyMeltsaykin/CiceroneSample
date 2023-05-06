package com.emeltsaykin.ciceronesample.utils

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.addOnBackPressedCallback(handler: () -> Unit) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handler.invoke()
        }
    }

    requireActivity().onBackPressedDispatcher.addCallback(this, callback)
}