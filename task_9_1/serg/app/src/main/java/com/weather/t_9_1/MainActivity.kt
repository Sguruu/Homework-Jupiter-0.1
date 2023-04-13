package com.weather.t_9_1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.weather.t_9_1.databinding.ActivityMainBinding

private const val TAG_LOG = "MyTest"
private const val KEY_FROM_SATE = "KEY_FROM_SATE"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fromState = FormState(true, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        outLog("onCreate")
    }

    override fun onStart() {
        super.onStart()
        outLog("onStart")
    }

    override fun onResume() {
        super.onResume()
        outLog("onResume")
    }

    override fun onPause() {
        super.onPause()
        outLog("onPause")
    }

    override fun onStop() {
        super.onStop()
        outLog("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        outLog("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outLog("onSaveInstanceState")

        outState.putParcelable(KEY_FROM_SATE, fromState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // версия SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // по ключу достанем наше состояние
            fromState = savedInstanceState.getParcelable(KEY_FROM_SATE, FormState::class.java)
                ?: error("Нет состояния")
        } else {
            fromState = savedInstanceState.getParcelable<FormState>(KEY_FROM_SATE)
                ?: error("Нет состояния")
        }
        renderTextError()
    }

    private fun initListener() {
        binding.buttonValidation.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        val loginPasswordNotEmpty =
            binding.editTextEmail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()

        if (loginPasswordNotEmpty) {
            emailValid()
        } else {
            fromState = fromState.setValid(
                false,
                resources.getString(R.string.error_email_and_password)
            )
            renderTextError()
        }
    }

    private fun emailValid() {
        val emailAddress = binding.editTextEmail.text.toString()
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()

        if (!isEmailValid) {
            fromState = fromState.setValid(false, resources.getString(R.string.error_email))
            renderTextError()
        } else {
            fromState = fromState.setValid(true)
            renderTextError()
        }
    }

    private fun renderTextError() {
        binding.textViewError.text = fromState.message
    }

    private fun outLog(textLog: String) {
        Log.v(TAG_LOG, textLog)
        Log.d(TAG_LOG, textLog)
        Log.i(TAG_LOG, textLog)
        Log.println(Log.ASSERT, TAG_LOG, textLog)
    }
}