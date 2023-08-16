package com.example.myapplication
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainInterface {
    private lateinit var binding: ActivityMainBinding
    private var friendList = ArrayList<Friend>()
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController
    }

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
//                supportFragmentManager.beginTransaction()
//                    .replace(binding.fragmentContainer.id, FragmentShowerListFriends.newInstance(
//                        ArrayList(), friendList))
//                    .commit()
                navController.navigate(R.id.action_global_fragmentShowerListFriends)
                return true
            }
        })

        (searchViewOnMenu.actionView as SearchView).setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
//                    supportFragmentManager.beginTransaction()
//                        .replace(binding.fragmentContainer.id,
//                            FragmentShowerListFriends.newInstance(filterFriendList(p0),
//                                friendList))
//                        .commit()
                    if (navController.currentDestination != navController.findDestination(R.id.fragmentShowerListFriends))
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
//        supportFragmentManager.beginTransaction()
//            .replace(binding.fragmentContainer.id, FragmentAdderFriends.newInstance(friendList))
//            .commit()
        if (navController.currentDestination != navController.findDestination(R.id.fragmentAdderFriends))
            navController.navigate(R.id.action_global_fragmentAdderFriends)
    }

    private fun showFriendList() {
//        supportFragmentManager.beginTransaction()
//            .replace(binding.fragmentContainer.id,
//                FragmentShowerListFriends.newInstance(ArrayList(),
//                    friendList))
//            .commit()
        if (navController.currentDestination != navController.findDestination(R.id.fragmentShowerListFriends))
            navController.navigate(R.id.action_global_fragmentShowerListFriends)
    }

//    private fun filterFriendList(valueSearch: String?) : ArrayList<Friend> {
//        val filterFriendList = ArrayList <Friend>()
//        friendList.filter {
//            it.name.contains(valueSearch ?: "", ignoreCase = true) ||
//                it.surname.contains(valueSearch ?: "", ignoreCase = true) ||
//                it.phoneNumber.contains(valueSearch ?: "", ignoreCase = true)
//        }
//            .let {
//                it.forEach {friend ->
//                    val newFriend = Friend(friend.name, friend.surname, friend.phoneNumber)
//                    filterFriendList.add(newFriend)
//                }
//            }
//        return filterFriendList
//    }

    override fun dellFriend(friend: Friend) {
        friendList.remove(friend)
    }
}