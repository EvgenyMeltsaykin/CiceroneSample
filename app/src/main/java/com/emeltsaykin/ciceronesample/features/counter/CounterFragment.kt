package com.emeltsaykin.ciceronesample.features.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.emeltsaykin.ciceronesample.R
import com.emeltsaykin.ciceronesample.base.BaseScopeFragment
import com.emeltsaykin.ciceronesample.databinding.FragmentCounterBinding
import com.emeltsaykin.ciceronesample.utils.fragmentArgument
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

internal class CounterFragment : BaseScopeFragment() {

    companion object {
        fun newInstance(route: CounterRoute): CounterFragment = CounterFragment().apply {
            this.route = route
        }
    }

    private val viewModel: CounterViewModel by viewModel()

    override val scopeModuleInstaller: () -> List<Module> = {
        val fragmentModule = module {
            scope<CounterFragment> {
                factory { route }
                viewModelOf(::CounterViewModel)
            }
        }
        listOf(fragmentModule)
    }

    private var _binding: FragmentCounterBinding? = null

    private val binding get() = _binding!!

    private var route by fragmentArgument<CounterRoute>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewModel.state.flowWithLifecycle(this.lifecycle)
            .onEach {
                binding.tvCounter.text = getString(R.string.counter_fragment_count, it.count)
                binding.tvFlowRouter.text = getString(R.string.counter_fragment_flow_router, it.flowRouter)
                binding.tvMainRouter.text = getString(R.string.counter_fragment_main_router, it.mainAppRouter)
                binding.tvSavedCounter.text = getString(R.string.counter_fragment_saved_counter, it.savedCounter)
                binding.tvCounterRepository.text = getString(R.string.counter_fragment_counter_repository, it.counterRepository)
            }.launchIn(this.lifecycleScope)
    }

    private fun setListeners() {
        with(binding) {
            btnNext.setOnClickListener {
                viewModel.onNextClicked()
            }

            btnNextFlow.setOnClickListener {
                viewModel.onNextFlowClicked()
            }

            btnCloseFlow.setOnClickListener {
                viewModel.onCloseFlowClicked()
            }

            btnBack.setOnClickListener {
                viewModel.onBackClicked()
            }

            btnReplace.setOnClickListener {
                viewModel.onReplaceClicked()
            }

            btnSaveCounter.setOnClickListener {
                viewModel.onSaveCounterClicked()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}