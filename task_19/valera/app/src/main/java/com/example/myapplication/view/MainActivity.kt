package com.example.myapplication.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.view_models.FriendViewModel
import com.example.myapplication.R
import com.example.myapplication.view_models.TownViewModel
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.view_models.InfoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController
    }
    private val friendViewModel: FriendViewModel by viewModels()
    private val townViewModel: TownViewModel by viewModels()
    private val infoViewModel: InfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        townViewModel.createDefaultList()
    }

    private fun initListener() {
        initToolbar()
        val searchViewOnMenu = binding.toolbar.menu.findItem(R.id.searcher)

        searchViewOnMenu.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                if (
                    navController.currentDestination == navController.findDestination(
                        R.id.fragmentAdderFriends
                    ) ||
                    navController.currentDestination == navController.findDestination(
                        R.id.fragmentShowerListFriends
                    )
                ) {
                    friendViewModel.resetFilterLiveData()
                    navController.navigate(R.id.action_global_fragmentShowerListFriends)
                } else {
                    townViewModel.resetFilterLiveData()
                    navController.navigate(R.id.action_global_fragmentShowerTowns)
                }

                return true
            }
        })

        (searchViewOnMenu.actionView as SearchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (
                    navController.currentDestination == navController.findDestination(
                        R.id.fragmentAdderFriends
                    ) ||
                    navController.currentDestination == navController.findDestination(
                        R.id.fragmentShowerListFriends
                    )
                ) {
                    friendViewModel.filterFriendList(p0)
                    navController.navigate(R.id.action_global_fragmentShowerListFriends)
                } else {
                    townViewModel.filterTowns(p0)
                    navController.navigate(R.id.action_global_fragmentShowerTowns)
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.searcher -> {
                    if (
                        navController.currentDestination == navController.findDestination(
                            R.id.fragmentAdderFriends
                        ) ||
                        navController.currentDestination == navController.findDestination(
                            R.id.fragmentShowerListFriends
                        )
                    ) {
                        showFriendList()
                    } else {
                        showTowns()
                    }
                    true
                }

                R.id.adder -> {
                    infoViewModel.updatePositionLiveData(null)
                    addFriendOnList()
                    true
                }

                R.id.shower -> {
                    showFriendList()
                    true
                }

                R.id.adder_town -> {
                    infoViewModel.updatePositionLiveData(null)
                    addTown()
                    true
                }

                R.id.shower_towns -> {
                    showTowns()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun addFriendOnList() {
        if (navController.currentDestination != navController.findDestination(R.id.fragmentAdderFriends))
            navController.navigate(R.id.action_global_fragmentAdderFriends)
    }

    private fun showFriendList() {
        navController.navigate(R.id.action_global_fragmentShowerListFriends)
    }

    private fun addTown() {
        if (navController.currentDestination != navController.findDestination(R.id.fragmentAdderTown)) {
            navController.navigate(R.id.action_global_fragmentAdderTown)
        }
    }

    private fun showTowns() {
        navController.navigate(R.id.action_global_fragmentShowerTowns)
    }
}