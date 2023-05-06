package com.emeltsaykin.ciceronesample.features.counter.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emeltsaykin.ciceronesample.R
import com.emeltsaykin.ciceronesample.base.BaseFlowFragment
import com.emeltsaykin.ciceronesample.databinding.FragmentContainerCounterBinding
import com.emeltsaykin.ciceronesample.features.counter.CounterNavigation
import com.emeltsaykin.ciceronesample.features.counter.CounterRoute
import com.emeltsaykin.ciceronesample.features.counter.data.CounterRepository
import com.emeltsaykin.ciceronesample.navigation.router.FlowRouter
import org.koin.android.ext.android.inject
import org.koin.core.module.Module
import org.koin.dsl.module

class CounterFlowContainer : BaseFlowFragment(R.id.container_counter_flow) {

    private var _binding: FragmentContainerCounterBinding? = null

    private val binding get() = _binding!!
    private val flowRouter: FlowRouter by inject()

    override val scopeModuleInstaller: () -> List<Module> = {
        val module = module {
            single { CounterRepository() }
        }
        listOf(module)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContainerCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            flowRouter.newRootScreen(CounterNavigation.counterScreen(CounterRoute(count = 0)))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}