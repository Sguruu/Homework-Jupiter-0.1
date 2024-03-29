package com.example.myapplication.view
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController
    }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        initToolbar()
        val searchViewOnMenu = binding.toolbar.menu.findItem(R.id.searcher)

        searchViewOnMenu.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                viewModel.resetFilterLiveData()
                navController.navigate(R.id.action_global_fragmentShowerListFriends)
                return true
            }
        })

        (searchViewOnMenu.actionView as SearchView).setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    viewModel.filterFriendList(p0)
                    navController.navigate(R.id.action_global_fragmentShowerListFriends)
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
                    showFriendList()
                    true
                }
                R.id.adder -> {
                    addFriendOnList()
                    true
                }
                R.id.shower -> {
                    showFriendList()
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
}