package com.emeltsaykin.ciceronesample.navigation

import com.emeltsaykin.ciceronesample.features.counter.CounterNavigation
import com.emeltsaykin.ciceronesample.navigation.screen.FlowFragmentScreen

object Screens {
    fun counterContainer(): FlowFragmentScreen = CounterNavigation.flowContainer()
}