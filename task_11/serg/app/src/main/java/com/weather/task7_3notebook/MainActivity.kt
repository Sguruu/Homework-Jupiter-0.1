package com.weather.task7_3notebook

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.weather.task7_3notebook.databinding.ActivityMainBinding
import com.weather.task7_3notebook.databinding.ItemContactBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toast: Toast
    private val listContact = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toast = Toast(this)

        initListener()
    }

    private fun initListener() {
        binding.buttonAddContact.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val numberPhone = binding.editTextNumber.text.toString().toIntOrNull() ?: 0

            binding.editTextName.setText("")
            binding.editTextLastName.setText("")
            binding.editTextNumber.setText("")

            listContact.add(Contact(name, lastName, numberPhone))
            renderProgressBar(resources.getString(R.string.save_contact))
        }

        binding.toolbar.setOnMenuItemClickListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.actionAddContact -> {
                    renderAddContact()
                    true
                }
                R.id.actionShowContent -> {
                    renderShowContactAll()
                    true
                }
                R.id.actionSearch -> {
                    renderActionSearch()
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
                    // удаление всех вью
                    binding.linearLayoutContent.removeAllViews()
                    // простой поиск по имени по нажатию кнопки
                    listContact.filter { it.name.contains(p0 ?: "", ignoreCase = true) }
                        .let { filterList ->
                            filterList.forEach { contact ->
                                createViewContent(contact)
                            }
                        }
                    return true
                }
            })
    }

    private fun renderAddContact() {
        binding.linearLayoutCreateUser.visibility = View.VISIBLE
        binding.linearLayoutContent.visibility = View.GONE
    }

    private fun renderShowContactAll() {
        // показать весь список
        binding.linearLayoutContent.removeAllViews()
        binding.linearLayoutCreateUser.visibility = View.GONE
        binding.linearLayoutContent.visibility = View.VISIBLE

        listContact.forEach { contact ->
            createViewContent(contact)
        }
    }

    private fun renderActionSearch() {
        binding.linearLayoutCreateUser.visibility = View.GONE
        binding.linearLayoutContent.visibility = View.VISIBLE
    }

    private fun createViewContent(contact: Contact) {
        // val view = layoutInflater.inflate(R.layout.item_contact, binding.linearLayoutContent, false)
        val view = ItemContactBinding.inflate(layoutInflater, binding.linearLayoutRoot, false)
        view.textViewName.text = contact.name
        view.textViewLastName.text = contact.lastName
        view.textViewNumberPhone.text = contact.number.toString()

        view.buttonDelete.setOnClickListener {
            listContact.remove(contact)
            binding.linearLayoutContent.removeView(view.root)
        }

        binding.linearLayoutContent.addView(view.root)
    }

    private fun renderProgressBar(text: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.buttonAddContact.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.buttonAddContact.isEnabled = true
            toast(text)
        }, 2000)
    }

    // пример изменение флага через Toolbar
//    private fun renderScrollFlagsToolbar(isActiveScroll: Boolean) {
//        val params = (binding.toolbar.layoutParams as AppBarLayout.LayoutParams)
//
//        when (isActiveScroll) {
//            true -> {
//                params.scrollFlags = SCROLL_FLAG_SCROLL + SCROLL_FLAG_ENTER_ALWAYS
//            }
//            false -> {
//                params.scrollFlags = SCROLL_FLAG_NO_SCROLL
//            }
//        }
//    }

    private fun toast(text: String) {
        toast.cancel()
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.show()
    }
}
