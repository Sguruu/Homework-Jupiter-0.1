package com.example.myapplication

import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tag = "Логирование ЖЦ Activity"
    private var errorLoginCondition = ErrorLoginCondition("")
    private val keyState = "KEY_STATE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.println(Log.VERBOSE, tag, message("onCreate"))
        initButton()
        createErrorMessageOnScreenRotation(savedInstanceState)
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
                errorLoginCondition = errorLoginCondition.emptyInput()
                binding.errorMessage.text = errorLoginCondition.message
            }
            else if(Patterns.EMAIL_ADDRESS.matcher(binding.editLogin.text.toString()).matches()){
                errorLoginCondition = errorLoginCondition.noError()
                binding.errorMessage.text = errorLoginCondition.message
            }
            else {
                errorLoginCondition = errorLoginCondition.invalidEmail()
                binding.errorMessage.text = errorLoginCondition.message
            }
        }
    }

    private fun createErrorMessageOnScreenRotation(savedInstanceState: Bundle?){
        errorLoginCondition =
            if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                savedInstanceState?.getParcelable(keyState, ErrorLoginCondition::class.java)?:
                ErrorLoginCondition("")
            } else {
                savedInstanceState?.getParcelable<ErrorLoginCondition>(keyState)?:
                ErrorLoginCondition("")
            }
        binding.errorMessage.text = errorLoginCondition.message
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(keyState, errorLoginCondition)
    }
}