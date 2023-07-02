package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var acceptCheckBox: CheckBox
    private lateinit var buttonLogin: Button
    private lateinit var wheel: ProgressBar
    private var invalidEmailMessage = "Incorrect mail input"
    private var checkEditTextLoginNoEmpty = false
    private var checkEditTextPasswordNoEmpty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        editTextLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextLoginNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextPasswordNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        acceptCheckBox.setOnCheckedChangeListener { _, _ ->
            renderButtonEnabled()
        }

        buttonLogin.setOnClickListener {
            if (Patterns.EMAIL_ADDRESS.matcher(editTextLogin.text.toString()).matches()){
                startWheel()
                val profileActivityIntent = Intent(
                    this, ProfileActivity::class.java
                )
                startActivity(profileActivityIntent)
                finish()
            } else {
                Toast.makeText(this,invalidEmailMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        editTextLogin = findViewById(R.id.edit_login)
        editTextPassword = findViewById(R.id.edit_password)
        acceptCheckBox = findViewById(R.id.accept)
        buttonLogin = findViewById(R.id.button_login)
        wheel = findViewById(R.id.wheel)
    }

    private fun startWheel() {
        wheel.visibility = View.VISIBLE
        editTextLogin.isEnabled = false
        editTextPassword.isEnabled = false
        acceptCheckBox.isEnabled = false
        buttonLogin.isEnabled = false
    }

    private fun renderButtonEnabled() {
        buttonLogin.isEnabled =
            checkEditTextLoginNoEmpty && checkEditTextPasswordNoEmpty && acceptCheckBox.isChecked
    }
}
