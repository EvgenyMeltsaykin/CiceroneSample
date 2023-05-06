package com.emeltsaykin.ciceronesample.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.emeltsaykin.ciceronesample.AppCode
import com.emeltsaykin.ciceronesample.koin.KoinScopeProvider
import com.emeltsaykin.ciceronesample.navigation.navigator.FlowNavigator
import com.emeltsaykin.ciceronesample.navigation.router.FlowRouter
import com.emeltsaykin.ciceronesample.navigation.router.MainAppRouter
import com.github.terrakok.cicerone.Cicerone
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.fragmentScope
import org.koin.core.module.Module
import org.koin.core.scope.Scope

private const val STATE_LAUNCH_FLAG = "state_launch_flag"
private const val STATE_SCOPE_NAME = "state_scope_name"

abstract class BaseFlowFragment(@IdRes containerId: Int) : Fragment(), KoinScopeProvider {

    private val featureScope: String? by lazy {
        (parentFragment as? BaseFlowFragment)?.fragmentScopeName
            ?: (parentFragment as? BaseScopeFragment)?.fragmentScopeName
    }
    override val scope: Scope by fragmentScope()

    private val mainAppRouter: MainAppRouter by inject()

    private val cicerone: Cicerone<FlowRouter> by lazy {
        Cicerone.create(FlowRouter(mainAppRouter))
    }

    private val flowRouter: FlowRouter by lazy {
        cicerone.router
    }

    private val navigator: FlowNavigator by lazy {
        FlowNavigator(
            activity = requireActivity(),
            containerId = containerId,
            fragmentManager = childFragmentManager,
            mainAppRouter = mainAppRouter
        )
    }
    protected open val scopeModuleInstaller: () -> List<Module> = { listOf() }

    private var instanceStateSaved: Boolean = false
    lateinit var fragmentScopeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        val savedAppCode = savedInstanceState?.getString(STATE_LAUNCH_FLAG)
        // False - if fragment was restored without new app process (for example: activity rotation)
        val isNewInAppProcess = savedAppCode != AppCode.appCode
        //fragmentScopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: scope.id
        fragmentScopeName = scope.id
        val scopeIsNotInit = isNewInAppProcess || getKoin().getScopeOrNull(fragmentScopeName)?.isNotClosed() == true
        val featureScope = featureScope
        if (featureScope != null) {
            scope.linkTo(getKoin().getScope(featureScope))
        }
        println("1234 scopeIsNotInit ")
        if (scopeIsNotInit) {
            scope.declare(flowRouter, allowOverride = true)
            getKoin().loadModules(modules = scopeModuleInstaller())
        }
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
        instanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        instanceStateSaved = true
        outState.putString(STATE_SCOPE_NAME, fragmentScopeName)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }
}