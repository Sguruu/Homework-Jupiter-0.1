package com.example.myapplication3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_view)
        textView.text = """
            APPLICATION_ID = ${BuildConfig.APPLICATION_ID}
            VERSION NAME = ${BuildConfig.VERSION_NAME}
            VERSION CODE = ${BuildConfig.VERSION_CODE}
            FLAVOR = ${BuildConfig.FLAVOR}
            BUILD TYPE = ${BuildConfig.BUILD_TYPE}
        """.trimIndent()
    }
}