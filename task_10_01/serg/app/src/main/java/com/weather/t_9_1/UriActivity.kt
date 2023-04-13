package com.weather.t_9_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.t_9_1.databinding.ActivityUriBinding

class UriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUriBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntentData()
    }

    private fun handleIntentData() {
        val scheme = intent.data?.scheme ?: ""
        val host = intent.data?.host ?: ""
        val pathPrefix = intent.data?.path ?: ""
        val result = "$scheme://${host}$pathPrefix"
        binding.textView.text = result
    }
}
