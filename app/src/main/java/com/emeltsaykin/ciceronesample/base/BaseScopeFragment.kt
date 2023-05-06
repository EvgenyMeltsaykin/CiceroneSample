package com.emeltsaykin.ciceronesample.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.emeltsaykin.ciceronesample.AppCode
import com.emeltsaykin.ciceronesample.features.counter.data.CounterRepository
import com.emeltsaykin.ciceronesample.koin.KoinScopeProvider
import com.emeltsaykin.ciceronesample.navigation.router.FlowRouter
import com.emeltsaykin.ciceronesample.utils.addOnBackPressedCallback
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.fragmentScope
import org.koin.core.module.Module
import org.koin.core.scope.Scope

private const val STATE_LAUNCH_FLAG = "state_launch_flag"
private const val STATE_SCOPE_NAME = "state_scope_name"

abstract class BaseScopeFragment() : Fragment(), KoinScopeProvider {
    protected open val featureScope: String by lazy {
        (parentFragment as? BaseFlowFragment)?.fragmentScopeName
            ?: (parentFragment as? BaseScopeFragment)?.fragmentScopeName
            ?: throw RuntimeException("Must be parent fragment!")
    }

    private var instanceStateSaved: Boolean = false
    lateinit var fragmentScopeName: String
    protected open val scopeModuleInstaller: () -> List<Module> = { listOf() }
    private val flowRouter: FlowRouter by inject()

    override val scope: Scope by fragmentScope()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addOnBackPressedCallback { flowRouter.exit() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val savedAppCode = savedInstanceState?.getString(STATE_LAUNCH_FLAG)
        // False - if fragment was restored without new app process (for example: activity rotation)
        val isNewInAppProcess = savedAppCode != AppCode.appCode
        //fragmentScopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: scope.id
        fragmentScopeName = scope.id
        val scopeIsNotInit = isNewInAppProcess || getKoin().getScopeOrNull(fragmentScopeName)?.isNotClosed() == true
        if (scopeIsNotInit) {
            getKoin().loadModules(modules = scopeModuleInstaller())
        }
        scope.linkTo(getKoin().getScope(featureScope))
        println("1234  featureScope $featureScope ${ getKoin().getScope(featureScope).get<CounterRepository>()}")
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        instanceStateSaved = true
        outState.putString(STATE_SCOPE_NAME, fragmentScopeName)
        outState.putString(STATE_LAUNCH_FLAG, AppCode.appCode)
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }
}