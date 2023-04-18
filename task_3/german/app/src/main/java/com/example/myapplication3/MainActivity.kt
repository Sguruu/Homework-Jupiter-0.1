package com.example.myapplication3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text_view = findViewById<TextView>(R.id.textView)

        text_view.text = """
            Version name = ${BuildConfig.VERSION_NAME}
            Flavor = ${BuildConfig.FLAVOR}
            Build Type = ${BuildConfig.BUILD_TYPE}
            APPLICATION_ID = ${BuildConfig.APPLICATION_ID}
        """.trimIndent()
    }
}