package com.emeltsaykin.ciceronesample.navigation.router

import com.emeltsaykin.ciceronesample.navigation.screen.FlowFragmentScreen
import com.github.terrakok.cicerone.Router

/**
 * Роутер используется внутри флоу фрагментов
 */
class FlowRouter(private val mainAppRouter: MainAppRouter) : Router() {

    fun navigateTo(screen: FlowFragmentScreen) {
        mainAppRouter.navigateTo(screen)
    }

    fun closeFlow() {
        mainAppRouter.exit()
    }
}