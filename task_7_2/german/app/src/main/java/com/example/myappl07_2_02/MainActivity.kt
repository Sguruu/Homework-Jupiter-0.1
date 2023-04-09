package com.example.myappl07_2_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappl07_2_02.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}