package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)

        textView.text = """
            APPLICATION_ID = ${BuildConfig.APPLICATION_ID}
            Build type = ${BuildConfig.BUILD_TYPE}
            FLAVOR = ${BuildConfig.FLAVOR}
            VERSION_CODE = ${BuildConfig.VERSION_CODE}
            VERSION_NAME = ${BuildConfig.VERSION_NAME} 
        """.trimIndent()
    }
}
