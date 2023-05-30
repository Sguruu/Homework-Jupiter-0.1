package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    val tag = "Логирование ЖЦ Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.println(Log.VERBOSE, tag, message("onCreate"))
    }

    override fun onStart() {
        super.onStart()
        Log.println(Log.DEBUG, tag, message("onCreate"))
    }

    override fun onResume() {
        super.onResume()
        Log.println(Log.INFO, tag, message("onStart"))
    }

    override fun onPause() {
        super.onPause()
        Log.println(Log.ERROR, tag, message("onPause"))
    }

    override fun onStop() {
        super.onStop()
        Log.println(Log.ASSERT, tag, message("onStop"))
    }

    override fun onRestart() {
        super.onRestart()
        Log.println(Log.VERBOSE, tag, message("onRestart"))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.println(Log.DEBUG, tag, message("onDestroy"))
    }

    fun message(funName:String):String{
        return "Запущен метод $funName"
    }
}