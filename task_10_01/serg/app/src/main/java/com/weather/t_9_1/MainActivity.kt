package com.weather.t_9_1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.weather.t_9_1.databinding.ActivityMainBinding

private const val KEY_FROM_SATE = "KEY_FROM_SATE"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fromState = FormState(true, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        inputValidationEditText()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
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
        binding.buttonLogin.setOnClickListener {
            openScreenProfile()
        }
    }

    private fun openScreenProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        // установка флагов при открытии активности
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        // закрытие текущей активности
        finish()
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
        binding.buttonLogin.isEnabled = fromState.valid
    }

    /**
     * Запуск проверки корректности введеного текста при вводе
     */
    private fun inputValidationEditText() {
        binding.editTextEmail.addTextChangedListener { validation() }
        binding.editTextPassword.addTextChangedListener { validation() }
    }
}
