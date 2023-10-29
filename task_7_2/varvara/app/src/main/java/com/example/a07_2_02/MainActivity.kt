package com.example.a07_2_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a07_2_02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.toolbarView.setTitle(R.string.text)

        binding.toolbarView.setNavigationOnClickListener {
            Toast.makeText(this, R.string.arrowBack, Toast.LENGTH_SHORT).show()
        }

        binding.toolbarView.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.Action_1 -> {
                    Toast.makeText(this, R.string.Action11, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Action_3 -> {
                    Toast.makeText(this, R.string.Action33, Toast.LENGTH_SHORT).show()
                     true
                }
                R.id.search -> {
                    Toast.makeText(this, R.string.Search, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.light -> {
                    Toast.makeText(this, R.string.light, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }

        }
    }


}