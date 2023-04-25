package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
      private val users = listOf(
    "Планета Юпитер готовит вторжение на Землю. К чему необходимо готовиться?",
    "Китай и Россия на самом деле не те, за кого себя выдают. На самом деле, они…",
    "Фольга из шапочки больше не помогает. Пермский маг рассказал, как защититься от новых ультразвуковых волн",
    "Он не тот, за кого себя выдаёт! Якутский шаман раскрыл тайну пермского самозванца",
    "Вас постоянно обманывают! С таким заявлением выступил главный герой романа Война и мир после вывода советских войск из Авганистана",
    "Шок! Певица Гурцкая на самом деле певица, но не Гурская",
    "Пенсионный возраст поднимать это вам не мешки ворочить. Президент Франции пожаловался на неблагодарный народ",
    "Пока Франция не стала частью России, всё же не стоит называть меня её президентом - Путин В.В.",
    "Каждое уважающее себя информ издание просто обязано хоть в одной новости упомянуть Северную Корею хотя бы дважды. Таковы тенденции - лидер Северной Кореи Ким Чен Ын",
    "Новость, на которую у меня уже не хватило фантазии, да я и не старался уже")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        searching()
    }

    private fun initToolbar(){
        binding.toolbar.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.del_phob -> { toastShowShort(R.string.del_phob_comp)
                    true}
                R.id.del_dis -> { toastShowShort(R.string.del_dis_comp)
                    true}
                R.id.add_pow -> { toastShowShort(R.string.add_power_comp)
                    true}
                else -> false
            }
        }
    }

    private fun searching(){
        val searchItem = binding.toolbar.menu.findItem(R.id.search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                changeNewsLine()
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                binding.textSearch.visibility = View.GONE
                binding.newsLine.visibility = View.VISIBLE
                binding.textSearch.text = R.string.null_search_query.toString()
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
                        .joinToString ("\n\n")
                        .let {
                            binding.textSearch.text = it }
                }
                else changeNewsLine()
                return true
            }
        })
    }
    private fun changeNewsLine (){
        binding.newsLine.visibility = View.GONE
        binding.textSearch.visibility = View.VISIBLE
    }

    private fun toastShowShort(text: Int){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }
}
