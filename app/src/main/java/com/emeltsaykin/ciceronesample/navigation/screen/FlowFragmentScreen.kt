package com.emeltsaykin.ciceronesample.navigation.screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface FlowFragmentScreen : FragmentScreen {
    override val clearContainer: Boolean get() = true
    override fun createFragment(factory: FragmentFactory): Fragment

    companion object {
        operator fun invoke(
            key: String? = null,
            clearContainer: Boolean = true,
            fragmentCreator: Creator<FragmentFactory, Fragment>
        ) = object : FlowFragmentScreen {
            override val screenKey = key ?: fragmentCreator::class.java.name
            override val clearContainer = clearContainer
            override fun createFragment(factory: FragmentFactory) = fragmentCreator.create(factory)
        }
    }
}