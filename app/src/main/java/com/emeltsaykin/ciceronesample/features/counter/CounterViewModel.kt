package com.emeltsaykin.ciceronesample.features.counter

import androidx.lifecycle.ViewModel
import com.emeltsaykin.ciceronesample.features.counter.data.CounterRepository
import com.emeltsaykin.ciceronesample.navigation.Screens
import com.emeltsaykin.ciceronesample.navigation.router.FlowRouter
import com.emeltsaykin.ciceronesample.navigation.router.MainAppRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class CounterViewModel(
    route: CounterRoute,
    mainAppRouter: MainAppRouter,
    private val flowRouter: FlowRouter,
    private val counterRepository: CounterRepository
) : ViewModel() {

    private val _state: MutableStateFlow<CounterState> = MutableStateFlow(
        CounterState(
            count = route.count,
            flowRouter = flowRouter.toString(),
            mainAppRouter = mainAppRouter.toString(),
            savedCounter = counterRepository.getSavedCounter(),
            counterRepository = counterRepository.toString()
        )
    )
    val state: StateFlow<CounterState> = _state.asStateFlow()

    fun onNextClicked() {
        val route = CounterRoute(count = state.value.count + 1)
        flowRouter.navigateTo(CounterNavigation.counterScreen(route))
    }

    fun onNextFlowClicked() {
        flowRouter.navigateTo(Screens.counterContainer())
    }

    fun onCloseFlowClicked() {
        flowRouter.closeFlow()
    }

    fun onReplaceClicked() {
        val route = CounterRoute(count = state.value.count + 1)
        flowRouter.replaceScreen(CounterNavigation.counterScreen(route))
    }

    fun onBackClicked() {
        flowRouter.exit()
    }

    fun onSaveCounterClicked() {
        counterRepository.saveCounter(state.value.count)
        _state.update { it.copy(savedCounter = state.value.count) }
    }
}