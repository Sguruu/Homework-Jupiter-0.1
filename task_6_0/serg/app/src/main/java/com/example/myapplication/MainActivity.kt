package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TOAST_TEXT = "Логин прошел успешно"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createProgressBar()
        initListener()
    }

    private fun initListener() {
        binding.button.setOnClickListener {
            val handler = Handler(Looper.getMainLooper())

            binding.checkbox.isEnabled = false
            binding.button.isEnabled = false

            binding.constraintLayout.addView(progressBar)
            handler.postDelayed({
                binding.constraintLayout.removeView(progressBar)
                binding.checkbox.isEnabled = true
                binding.button.isEnabled = true
                Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG)
                    .show()
            }, 2000)
        }

        binding.emailEditText.addTextChangedListener { isEnableButton() }
        binding.passwordEditText.addTextChangedListener { isEnableButton() }
        binding.checkbox.setOnCheckedChangeListener { _, _ -> isEnableButton() }
    }

    /***
     * Программное создание progressBar
     */
    private fun createProgressBar() {
        val params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        params.startToStart = binding.constraintLayout.id
        params.bottomToBottom = binding.constraintLayout.id
        params.topToTop = binding.constraintLayout.id
        params.endToEnd = binding.constraintLayout.id

        progressBar = ProgressBar(this).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        }

        progressBar.layoutParams = params

        binding.emailEditText.text.toString()
    }

    /***
     * Проверка на условия, для активации - деактивации кнопки
     */
    private fun isEnableButton() {
        binding.button.isEnabled =
            binding.emailEditText.text.toString() != "" &&
            binding.passwordEditText.text.toString() != "" &&
            binding.checkbox.isChecked
    }
}
