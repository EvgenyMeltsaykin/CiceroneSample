package com.emeltsaykin.ciceronesample.features.counter

import com.emeltsaykin.ciceronesample.features.counter.flow.CounterFlowContainer
import com.emeltsaykin.ciceronesample.navigation.screen.FlowFragmentScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

internal object CounterNavigation {

    fun flowContainer(): FlowFragmentScreen = FlowFragmentScreen { CounterFlowContainer() }

    internal fun counterScreen(route: CounterRoute): FragmentScreen = FragmentScreen { CounterFragment.newInstance(route) }

}