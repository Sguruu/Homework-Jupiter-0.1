package com.example.myapplication

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
            Version code = ${BuildConfig.VERSION_CODE}
            Flavor = ${BuildConfig.FLAVOR}
            Build type = ${BuildConfig.BUILD_TYPE}
            APLICATION_ID = ${BuildConfig.APPLICATION_ID}
        """.trimIndent()
    }
}