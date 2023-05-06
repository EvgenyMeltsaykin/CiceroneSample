package com.emeltsaykin.ciceronesample.features.counter.data

class CounterRepository {
    private var savedCounter: Int? = null


    fun saveCounter(count: Int) {
        savedCounter = count
    }

    fun getSavedCounter(): Int? = savedCounter
}