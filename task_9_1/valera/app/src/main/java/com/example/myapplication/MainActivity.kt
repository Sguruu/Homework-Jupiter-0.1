package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val tag = "Логирование ЖЦ Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.println(Log.VERBOSE, tag, message("onCreate"))
        initButton()
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

    private fun message(funName:String):String{
        return "Запущен метод $funName"
    }

    private fun initButton(){
        binding.loginButton.setOnClickListener {
            if (binding.editLogin.text.isEmpty() &&
                binding.editPassword.text.isEmpty()
            ){binding.errorMessage.text = "Ошибка. Не введён логин, либо пароль"}
            else if(Patterns.EMAIL_ADDRESS.matcher(binding.editLogin.text.toString()).matches()){
                binding.errorMessage.text = " "
            }
            else {
                binding.errorMessage.text = "Ошибка. Введён некорректный адрес электронной почты"}
        }
    }
}