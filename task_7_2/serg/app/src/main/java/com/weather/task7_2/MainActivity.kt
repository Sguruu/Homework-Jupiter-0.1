package com.weather.task7_2

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.weather.task7_2.databinding.ActivityMainBinding

private const val NUMBER_LINES_LONG_CONTENT = 134

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // создание одного объекта уведомления
        toast = Toast(this)

        // генерация и присвоение текста контента
        binding.TextView.text =
            generateСontent(NUMBER_LINES_LONG_CONTENT).joinToString(separator = "")

        initListener()
    }

    /**
     * Инициализация слушателей
     */
    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            toast(resources.getString(R.string.text_arrow_button_click))
        }

        binding.toolbar.setOnMenuItemClickListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.action_1 -> {
                    toast("Нажат action_1")
                    true
                }
                R.id.action_2 -> {
                    toast("Нажат action_2")
                    true
                }
                R.id.action_3 -> {
                    toast("Нажат action_3")
                    true
                }
                R.id.action_search -> {
                    toast("Нажат action_search")
                    true
                }
                else -> {
                    false
                }
            }
        }

        val searchItem = binding.toolbar.menu.findItem(R.id.action_search)

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                toast("Нажат onMenuItemActionExpand")
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                toast("Нажат onMenuItemActionCollapse")
                return true
            }
        })

        (searchItem.actionView as SearchView).setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchToast(p0)
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    searchToast(p0)
                    return true
                }
            })
    }

    /**
     * Отображение поисковых слов в уведомлении
     * @param text поисковый текст/фраза
     */
    private fun searchToast(text: String?) {
        text?.let { toast("${resources.getString(R.string.search)} $text") }
    }

    /**
     * Закрытие и показ уведомления
     * @param text текст уведомления
     */
    private fun toast(text: String) {
        // Закрытие текущего уведомления при вызове нового
        toast.cancel()
        // Создание нового уведомления
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        // Показ нового уведомления
        toast.show()
    }

    /**
     * Генерация контента для тестирования
     * @param numberLines количество строк конетнта
     */
    private fun generateСontent(numberLines: Int = 20): List<String> {
        val list: MutableList<String> = MutableList(size = numberLines) {
            "${resources.getString(R.string.long_content)} ${it.inc()}\n"
        }
        return list
    }
}
