package com.weather.task7_3notebook.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.weather.task7_3notebook.R
import com.weather.task7_3notebook.databinding.ActivityMainBinding
import com.weather.task7_3notebook.viewmodel.MainViewModel

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
    }

    private fun initListener() {
        binding.toolbar.setOnMenuItemClickListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.actionAddContact -> {
                    showAddPersonFragment()
                    true
                }

                R.id.actionAddCity -> {
                    true
                }

                R.id.actionShowCity -> {
                    true
                }

                R.id.actionShowContact -> {
                    showListFragment()
                    true
                }

                R.id.actionSearch -> {
                    showListFragment()
                    true
                }

                else -> {
                    false
                }
            }
        }

        val actionSearch = binding.toolbar.menu.findItem(R.id.actionSearch)

        actionSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                return true
            }
        })

        (actionSearch.actionView as SearchView).setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    showListFragment()
                    // простой поиск по имени по нажатию кнопки
                    viewModel.search(p0)
                    return true
                }
            })
    }

    private fun showListFragment() {
        if (navController.currentDestination != navController.findDestination(R.id.listFragment)) {
            navController.navigate(R.id.action_addPersonFragment_to_listFragment)
        }
    }

    private fun showAddPersonFragment() {
        if (navController.currentDestination != navController.findDestination(R.id.addPersonFragment)) {
            navController.navigate(R.id.action_global_addPersonFragment)
        }
    }
}
