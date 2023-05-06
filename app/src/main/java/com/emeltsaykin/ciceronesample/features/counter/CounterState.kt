package com.emeltsaykin.ciceronesample.features.counter

import com.emeltsaykin.ciceronesample.features.counter.data.CounterRepository

internal data class CounterState(
    val count: Int,
    val flowRouter: String,
    val mainAppRouter: String,
    val counterRepository: String,
    val savedCounter: Int?
)