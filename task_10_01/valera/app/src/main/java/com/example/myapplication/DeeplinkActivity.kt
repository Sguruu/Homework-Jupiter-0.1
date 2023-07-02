package com.example.myapplication
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityDeeplinkBinding

class DeeplinkActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDeeplinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeeplinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vkTextView.text = intent?.data?.toString()?: " "
    }
}