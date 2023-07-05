package com.example.myapplication
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val friendList = ArrayList<Friend>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initListener()
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.searcher -> {
                    showerList()
                    true
                }
                R.id.adder -> {
                    renderAdderFriend()
                    true
                }
                R.id.shower -> {
                    showerList()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun initListener() {
        val searchViewOnMenu = binding.toolbar.menu.findItem(R.id.searcher)

        searchViewOnMenu.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, FragmentShowerListFriends.newInstance(
                        ArrayList(), friendList))
                    .commit()
                return true
            }
        })

        (searchViewOnMenu.actionView as SearchView).setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    val filterFriendList = filterFriendList(p0)
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id,
                            FragmentShowerListFriends.newInstance(filterFriendList,
                                friendList))
                        .commit()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            })
    }

    private fun renderAdderFriend() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, FragmentAdderFriends.newInstance(friendList))
            .commit()
    }

    private fun showerList() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id,
                FragmentShowerListFriends.newInstance(ArrayList(),
                    friendList))
            .commit()
    }

    private fun filterFriendList(valueSearch: String?) : ArrayList<Friend> {
        val filterFriendList = ArrayList <Friend>()
        friendList.filter {
            it.name.contains(valueSearch ?: "", ignoreCase = true) ||
                it.surname.contains(valueSearch ?: "", ignoreCase = true) ||
                it.phoneNumber.contains(valueSearch ?: "", ignoreCase = true)
        }
            .let {
                it.forEach {friend ->
                    val newFriend = Friend(friend.name, friend.surname, friend.phoneNumber)
                    filterFriendList.add(newFriend)
                }
            }
        return filterFriendList
    }
}