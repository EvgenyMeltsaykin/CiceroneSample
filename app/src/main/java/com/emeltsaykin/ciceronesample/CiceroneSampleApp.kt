package com.emeltsaykin.ciceronesample

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.emeltsaykin.ciceronesample.navigation.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.UUID

class CiceroneSampleApp : Application() {

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate()
        AppCode.appCode = UUID.randomUUID().toString()
        startKoin {
            androidContext(this@CiceroneSampleApp)
            androidLogger(Level.ERROR)
            modules(navigationModule)
        }
    }
}