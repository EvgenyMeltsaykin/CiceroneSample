package com.emeltsaykin.ciceronesample.navigation

import com.emeltsaykin.ciceronesample.navigation.router.MainAppRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.core.module.Module
import org.koin.dsl.module

val navigationModule: Module = module {
    val cicerone = Cicerone.create(MainAppRouter())
    single<NavigatorHolder> { cicerone.getNavigatorHolder() }
    single<MainAppRouter>() { cicerone.router }
}