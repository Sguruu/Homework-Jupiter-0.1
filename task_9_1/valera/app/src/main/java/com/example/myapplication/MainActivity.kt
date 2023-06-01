package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tag = "Логирование ЖЦ Activity"
    private lateinit var errorLoginCondition: String
    private val keyState = "KEY_STATE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.println(Log.VERBOSE, tag, message("onCreate"))
        initButton()
        errorLoginCondition = savedInstanceState?.getString(keyState)?: ""
        binding.errorMessage.text = errorLoginCondition
    }

    override fun onStart() {
        super.onStart()
        Log.println(Log.DEBUG, tag, message("onStart"))

    }

    override fun onResume() {
        super.onResume()
        Log.println(Log.INFO, tag, message("onResume"))
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
            if (binding.editLogin.text.isEmpty() ||
                binding.editPassword.text.isEmpty()
            ){
                binding.errorMessage.text = ErrorLogin.EMPTYINPUT.text
                errorLoginCondition = ErrorLogin.EMPTYINPUT.text
            }
            else if(Patterns.EMAIL_ADDRESS.matcher(binding.editLogin.text.toString()).matches()){
                binding.errorMessage.text = ""
                errorLoginCondition = ""
            }
            else {
                binding.errorMessage.text = ErrorLogin.INVALIDEMAIL.text
                errorLoginCondition = ErrorLogin.INVALIDEMAIL.text
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(keyState, errorLoginCondition)
    }
}