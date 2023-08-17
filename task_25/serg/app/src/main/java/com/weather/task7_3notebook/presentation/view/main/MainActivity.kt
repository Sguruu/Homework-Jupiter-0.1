package com.weather.task7_3notebook.presentation.view.main

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.weather.task7_3notebook.R
import com.weather.task7_3notebook.databinding.ActivityMainBinding
import com.weather.task7_3notebook.presentation.extensions.textChangedFlow
import com.weather.task7_3notebook.presentation.view.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // отключение темной темы
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initListener()
        initObserve()
    }

    private fun initListener() {
        binding.toolbar.setOnMenuItemClickListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.actionAddContact -> {
                    showAddPersonFragment()
                    true
                }

                R.id.actionAddCity -> {
                    showAddCityFragment()
                    true
                }

                R.id.actionShowCity -> {
                    showListCityFragment()
                    true
                }

                R.id.actionShowContact -> {
                    showListContactFragment()
                    true
                }

                R.id.actionSearch -> {
                    if (navController.currentDestination !=
                        navController.findDestination(R.id.listContactFragment) &&
                        navController.currentDestination !=
                        navController.findDestination(R.id.listCityFragment)
                    ) {
                        showListContactFragment()
                    }
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            flowTextSearchView()
                .debounce(400)
                .distinctUntilChanged()
                .mapLatest {
                    viewModel.updateSearchFlow(it)
                }
                .flowOn(Dispatchers.IO)
                .collect()
        }
    }

    private fun flowTextSearchView(): Flow<String> {
        val actionSearch = binding.toolbar.menu.findItem(R.id.actionSearch)
        return (actionSearch.actionView as SearchView).textChangedFlow()
    }

    private fun showListContactFragment() {
        if (navController.currentDestination != navController.findDestination(R.id.listContactFragment)) {
            navController.navigate(R.id.action_global_listFragment)
        }
    }

    private fun showAddPersonFragment() {
        if (navController.currentDestination != navController.findDestination(R.id.addPersonFragment)) {
            navController.navigate(R.id.action_global_addPersonFragment)
        }
    }

    private fun showAddCityFragment() {
        if (navController.currentDestination != navController.findDestination(R.id.addCityFragment)) {
            navController.navigate(R.id.action_global_addCityFragment)
        }
    }

    private fun showListCityFragment() {
        if (navController.currentDestination != navController.findDestination(R.id.listCityFragment)) {
            navController.navigate(R.id.action_global_listCityFragment)
        }
    }
}
