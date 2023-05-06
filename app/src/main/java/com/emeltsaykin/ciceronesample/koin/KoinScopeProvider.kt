package com.emeltsaykin.ciceronesample.koin

import org.koin.android.scope.AndroidScopeComponent
import org.koin.core.scope.Scope

interface KoinScopeProvider : AndroidScopeComponent {
    override val scope: Scope
}