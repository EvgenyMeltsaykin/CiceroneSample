package com.emeltsaykin.ciceronesample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emeltsaykin.ciceronesample.databinding.ActivityMainBinding
import com.emeltsaykin.ciceronesample.navigation.Screens
import com.emeltsaykin.ciceronesample.navigation.navigator.MainNavigator
import com.emeltsaykin.ciceronesample.navigation.router.MainAppRouter
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        MainNavigator(
            activity = this,
            containerId = R.id.fragment_container,
        )
    }

    private val navigationHolder by inject<NavigatorHolder>()
    private val mainAppRouter by inject<MainAppRouter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainAppRouter.newRootScreen(Screens.counterContainer())
        binding.bnvNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuTabOne -> mainAppRouter.newRootScreen(Screens.counterContainer()).let { true }
                R.id.menuTabTwo -> mainAppRouter.newRootScreen(Screens.counterContainer()).let { true }
                R.id.menuTabThree -> mainAppRouter.newRootScreen(Screens.counterContainer()).let { true }
                else -> false
            }
        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }
}