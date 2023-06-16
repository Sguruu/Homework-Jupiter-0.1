package com.example.myapplication07_2_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import com.example.myapplication07_2_02.databinding.ActivityMainBinding

private const val NUMBER_LINES_LONG_CONTENT = 1000
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var barToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        binding.textView.text =
            generateInteg(NUMBER_LINES_LONG_CONTENT).joinToString(separator = "")
    }

    private fun initToolbar() {
        binding.ToolBarik.setNavigationOnClickListener {
            notiToast(resources.getString(R.string.naviBut))
        }
        binding.ToolBarik.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action1 -> {
                    toastFun(resources.getString(R.string.action_1))
                    true
                }
                R.id.action2 -> {
                    toastFun(resources.getString(R.string.action_2))
                    true
                }
                R.id.action3 -> {
                    toastFun(resources.getString(R.string.action_3))
                    true

                }
                else -> {
                    false
                }
            }
        }

        val searchItem = binding.ToolBarik.menu.findItem(R.id.search)

        searchItem.setOnActionExpandListener(
            object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                    notiToast("Нажат onMenuItemActionExpand")
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                    notiToast("Нажат onMenuItemActionCollapse")
                    return true
                }
            })

        (searchItem.actionView as SearchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(SearchText: String?): Boolean {
                searchToast(SearchText)
                return true
            }

            override fun onQueryTextChange(SearchText: String?): Boolean {
                searchToast(SearchText)
                return true
            }
        })
    }
    private fun searchToast(text: String?){
        text?.let { toastFun("${resources.getString(R.string.searchWindow)} $text") }
    }
    private fun notiToast(text: String) {
        barToast.cancel()
        barToast = Toast.makeText(this, text, Toast.LENGTH_LONG)
        barToast.show()
    }

    private fun generateInteg(num: Int = 1): List<String>{
        val list: MutableList<String> = MutableList(size = num) {
            "${resources.getString(R.string.name_string)} ${it.inc()}\n"
        }
        return list
    }
    private fun toastFun(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
