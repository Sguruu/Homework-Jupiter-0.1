package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ShowerListBinding

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

//                    binding.welcomeWords.visibility = View.GONE
//                    binding.showerList.visibility = View.GONE
                    binding.searcherLinerLayout.visibility = View.VISIBLE
//                  binding.adderFriend.visibility = View.GONE
                    supportFragmentManager.beginTransaction()
                        .remove(FragmentAdderFriends())
                        .commit()

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
                binding.searcherLinerLayout.visibility = View.GONE
                binding.searcherLinerLayout.removeAllViews()
//                binding.showerList.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, FragmentShowerListFriends.newInstance(friendList))
                    .commit()
                return true
            }
        })

        (searchViewOnMenu.actionView as SearchView).setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    binding.searcherLinerLayout.removeAllViews()
                    searcher(p0)
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            })
    }

    private fun renderAdderFriend() {
//        binding.welcomeWords.visibility = View.GONE

//        binding.showerList.visibility = View.GONE
        binding.searcherLinerLayout.visibility = View.GONE

//      binding.adderFriend.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, FragmentAdderFriends.newInstance(friendList))
            .commit()
    }
    private fun showerList() {
//        binding.welcomeWords.visibility = View.GONE
        binding.searcherLinerLayout.visibility = View.GONE
//        binding.showerList.visibility = View.VISIBLE
//      binding.adderFriend.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, FragmentShowerListFriends.newInstance(friendList))
            .commit()
    }

    private fun searcher(valueSearch: String?) {
        friendList.filter {
            it.name.contains(valueSearch ?: "", ignoreCase = true) ||
                it.surname.contains(valueSearch ?: "", ignoreCase = true) ||
                it.phoneNumber.contains(valueSearch ?: "", ignoreCase = true)
        }
            .let {
                it.forEach {
                    renderViewResultSearch(it)
                }
            }
    }

    private fun renderViewResultSearch(friend: Friend) {
        val viewOnSearch = ShowerListBinding.inflate(
            layoutInflater,
            binding.searcherLinerLayout,
            false
        )

        viewOnSearch.apply {
            viewOnSearch.friendName.text = friend.name
            viewOnSearch.friendSurname.text = friend.surname
            viewOnSearch.friendPhoneNumber.text = friend.phoneNumber
            viewOnSearch.buttomDellFriend.visibility = View.GONE
        }
        binding.searcherLinerLayout.addView(viewOnSearch.root)
    }
}
