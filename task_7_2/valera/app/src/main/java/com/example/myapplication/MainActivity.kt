package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val users = listOf("Валера", "Варя", "Саша", "Герман", "Серёжа" )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()

    }

    private fun initToolbar(){
        binding.toolbar.setNavigationOnClickListener{
            toastShowShort("Нажата кнопка назад")
        }
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.del_phob -> { toastShowShort("Фобия устранена")
                    true}
                R.id.del_dis -> { toastShowShort("Болезнь вылечена")
                    true}
                R.id.add_pow -> { toastShowShort("Заряд получен")
                    true}
                else -> false
            }
        }
        val searchItem = binding.toolbar.menu.findItem(R.id.search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                zeroingText()
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                binding.news1.text = "Планета Юпитер готовит вторжение на Землю. К чему необходимо готовиться?"
                return true
            }

        })

        (searchItem.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0?.isEmpty() == false){
                    users.filter { it.contains(p0, ignoreCase = true) }
                        .joinToString ()
                        .let { binding.news1.text = it }
                }
                else zeroingText()
                return true
            }
        })
    }

    private fun checkContentSearch(){

    }
    private fun zeroingText (){
        binding.news1.text = ""
    }

    private fun toastShowShort(text:String){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }
}