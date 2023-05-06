package com.emeltsaykin.ciceronesample.navigation.navigator

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import com.emeltsaykin.ciceronesample.navigation.extensions.isBackStackEmpty
import com.emeltsaykin.ciceronesample.navigation.router.MainAppRouter
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator

class FlowNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager,
    fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory,
    private val mainAppRouter: MainAppRouter,
) : AppNavigator(activity, containerId, fragmentManager, fragmentFactory) {

    override fun applyCommand(command: Command) {
        when (command) {
            is Back -> {
                if (fragmentManager.isBackStackEmpty()) {
                    mainAppRouter.exit()
                } else {
                    super.applyCommand(command)
                }
            }
            else -> {
                super.applyCommand(command)
            }
        }
    }
}