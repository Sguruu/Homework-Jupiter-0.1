package com.example.task05


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity() {


    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var acceptCheckBox: CheckBox
    private lateinit var buttonLogin: Button
    private lateinit var wheel: ProgressBar
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
            startWheel()
        }
    }
    private fun initView(){
        editTextLogin = findViewById(R.id.edit_login)
        editTextPassword = findViewById(R.id.edit_password)
        acceptCheckBox = findViewById(R.id.accept)
        buttonLogin = findViewById(R.id.enter)
        wheel = findViewById(R.id.wheel)
    }

    private fun startWheel() {
        wheel.visibility = View.VISIBLE
        editTextLogin.isEnabled = false
        editTextPassword.isEnabled = false
        acceptCheckBox.isEnabled = false
        buttonLogin.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            wheel.visibility = View.GONE
            editTextLogin.isEnabled = true
            editTextPassword.isEnabled = true
            acceptCheckBox.isEnabled = true
            buttonLogin.isEnabled = true
            Toast.makeText(
                this,
                resources.getString(R.string.good_enter),
                Toast.LENGTH_SHORT
            ).show()
        }, 2000)
    }

    private fun renderButtonEnabled() {
        buttonLogin.isEnabled =
            checkEditTextLoginNoEmpty && checkEditTextPasswordNoEmpty && acceptCheckBox.isChecked
    }

}