package com.example.a03_01_02

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
//показ данных
        textView.text = """
            APPLICATION_ID = ${BuildConfig.APPLICATION_ID}
            Build type = ${BuildConfig.BUILD_TYPE}
            FLAVOR = ${BuildConfig.FLAVOR}
            VERSION_CODE = ${BuildConfig.VERSION_CODE}
            VERSION_NAME = ${BuildConfig.VERSION_NAME} 
        """.trimIndent()


    }
}
