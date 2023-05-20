package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ShowerListBinding

class MainActivity : AppCompatActivity() {
    private var checkEditTextNameNoEmpty = false
    private var checkEditTextSurnameNoEmpty = false
    private var checkEditTextPhoneNumberNoEmpty = false
    private lateinit var binding: ActivityMainBinding
    private val friendList = mutableListOf<Friend>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
    }
    private fun initToolbar(){
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId){

                R.id.searcher->{
                    searcher()
                    true}

                R.id.adder ->{
                    adderFriend()
                    true}

                R.id.shower->{
                    showerList()
                    true}

                else ->{false}
            }
        }
    }
    private fun adderFriend(){
        binding.welcomeWords.visibility = View.GONE
        binding.showerList.visibility = View.GONE
        binding.searcherLinerLayout.visibility = View.GONE
        binding.adderFriend.visibility = View.VISIBLE

        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextNameNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.edinSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextSurnameNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.editPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextPhoneNumberNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.adderButtom.setOnClickListener {
            startWheel()
            addFriendOnList()
            finishWheel()
        }
    }

    private fun startWheel() {
        binding.wheel.visibility = View.VISIBLE
        binding.editName.isEnabled = false
        binding.edinSurname.isEnabled = false
        binding.editPhoneNumber.isEnabled = false
        binding.adderButtom.isEnabled = false
    }

    private fun addFriendOnList(){
            friendList.add(Friend(
                binding.editName.text.toString(),
                binding.edinSurname.text.toString(),
                binding.editPhoneNumber.text.toString()))

        val view = ShowerListBinding.inflate(layoutInflater, binding.showerList, false)

        view.apply {
            view.friendName.text = binding.editName.text.toString()
            view.friendSurname.text = binding.edinSurname.text.toString()
            view.friendPhoneNumber.text = binding.editPhoneNumber.text.toString()

            view.buttomDellFriend.setOnClickListener {
                for (i in 0 until friendList.size){
                    if (friendList[i].name == view.friendName.text &&
                        friendList[i].surname == view.friendSurname.text &&
                        friendList[i].phoneNumber == view.friendPhoneNumber.text
                    ){
                        friendList.remove(friendList[i])
                        break
                    }
                }
                binding.showerList.removeView(view.root)
            }
        }
        binding.showerList.addView(view.root)
    }

    private fun finishWheel(){
        Handler(Looper.getMainLooper()).postDelayed({
            binding.wheel.visibility = View.GONE
            binding.editName.isEnabled = true
            binding.editName.text = null
            binding.edinSurname.isEnabled = true
            binding.edinSurname.text = null
            binding.editPhoneNumber.isEnabled = true
            binding.editPhoneNumber.text = null
            Toast.makeText(
                this,
                resources.getString(R.string.successfully),
                Toast.LENGTH_SHORT
            ).show()
        }, 2000)
    }

    private fun renderButtonEnabled() {
        binding.adderButtom.isEnabled =
            checkEditTextNameNoEmpty && checkEditTextSurnameNoEmpty && checkEditTextPhoneNumberNoEmpty
    }

    private fun showerList(){
        binding.welcomeWords.visibility = View.GONE
        binding.adderFriend.visibility = View.GONE
        binding.searcherLinerLayout.visibility = View.GONE
        binding.showerList.visibility = View.VISIBLE
    }

    private fun searcher(){
        binding.welcomeWords.visibility = View.GONE
        binding.adderFriend.visibility = View.GONE
        binding.showerList.visibility = View.GONE
        binding.searcherLinerLayout.visibility = View.VISIBLE



        val searchViewOnMenu = binding.toolbar.menu.findItem(R.id.searcher)

        searchViewOnMenu.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                return true
            }
            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                binding.searcherLinerLayout.visibility = View.GONE
                binding.searcherLinerLayout.removeAllViews()
                binding.showerList.visibility = View.VISIBLE
                return true
            }
        })

        (searchViewOnMenu.actionView as SearchView).setOnQueryTextListener( object :
        SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searcherLinerLayout.removeAllViews()

                friendList.filter {
                    it.name.contains(p0 ?: "", ignoreCase = true) ||
                    it.surname.contains(p0 ?: "", ignoreCase = true) ||
                    it.phoneNumber.contains(p0 ?: "", ignoreCase = true)
                }
                    .let {
                        it.forEach {
                            val viewOnSearch = ShowerListBinding.inflate(layoutInflater, binding.searcherLinerLayout, false)

                            viewOnSearch.apply {
                                viewOnSearch.friendName.text = it.name
                                viewOnSearch.friendSurname.text = it.surname
                                viewOnSearch.friendPhoneNumber.text = it.phoneNumber
                                viewOnSearch.buttomDellFriend.visibility = View.GONE
                            }
                            binding.searcherLinerLayout.addView(viewOnSearch.root)
                        }
                    }
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })


    }

}


