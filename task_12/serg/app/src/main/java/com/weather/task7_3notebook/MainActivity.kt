package com.weather.task7_3notebook

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.weather.task7_3notebook.databinding.ActivityMainBinding

interface IMainActivity {
    /**
     * Добавить контакт в список MainActivity
     */
    fun addContact(contact: Contact)

    /**
     * Добавить удаление контактка из списка MainActivity
     */
    fun removeContact(contact: Contact)

    /**
     * Получить список контактов из MainActivity
     */
    fun getListContact(): List<Contact>
}

class MainActivity : AppCompatActivity(), IMainActivity {

    private lateinit var binding: ActivityMainBinding
    private val listContact = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showAddPersonFragment()
        }
        // отключение темной темы
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initListener()
    }

    override fun addContact(contact: Contact) {
        listContact.add(contact)
    }

    override fun removeContact(contact: Contact) {
        listContact.remove(contact)
    }

    override fun getListContact(): List<Contact> {
        return listContact
    }

    private fun initListener() {
        binding.toolbar.setOnMenuItemClickListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.actionAddContact -> {
                    replaceAddPersonFragment()
                    true
                }

                R.id.actionShowContent -> {
                    replaceListFragment()
                    true
                }

                R.id.actionSearch -> {
                    replaceListFragment()
                    // renderActionSearch()
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
                    // простой поиск по имени по нажатию кнопки
                    listContact.filter { it.name.contains(p0 ?: "", ignoreCase = true) }
                        .let { filterList ->
                            // проверка что сейчас показывается нужный фрагмент
                            val currentFragment =
                                supportFragmentManager.findFragmentById(
                                    R.id.fragmentContainerView1
                                )
                            if (currentFragment != null &&
                                currentFragment.tag == ListFragment.FRAGMENT_TAG
                            ) {
                                (currentFragment as IListFragment).processSearchResult(filterList)
                            }
                        }
                    return true
                }
            })
    }

    private fun replaceListFragment() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(
                binding.fragmentContainerView1.id,
                ListFragment(),
                ListFragment.FRAGMENT_TAG
            )
            .commit()
    }

    private fun showAddPersonFragment() {
        supportFragmentManager.beginTransaction()
            .add(
                binding.fragmentContainerView1.id,
                AddPersonFragment(),
                AddPersonFragment.FRAGMENT_TAG
            )
            .setReorderingAllowed(true)
            .commit()
    }

    private fun replaceAddPersonFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView1)
        if (currentFragment != null && currentFragment.tag != AddPersonFragment.FRAGMENT_TAG) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(
                    binding.fragmentContainerView1.id,
                    AddPersonFragment(),
                    AddPersonFragment.FRAGMENT_TAG
                )
                .commit()
        }
    }
}
