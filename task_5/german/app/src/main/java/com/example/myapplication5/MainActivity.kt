package com.example.myapplication5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
private lateinit var textView: TextView
private lateinit var inputEmail: EditText
private lateinit var inputPassword: EditText
private lateinit var checkBoxAgree: CheckBox
private lateinit var buttonOk: Button
private lateinit var progressBar: ProgressBar

private var checkEmailEmptyorNo = false
private var checkPasswordEmptyorNo = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find()

        inputEmail.addTextChangedListener(object : overTextWatch() {
            override fun onTextChanged(write: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEmailEmptyorNo = write?.isNotEmpty()?: false
                emptyOrNo()
            }
        })

        inputPassword.addTextChangedListener(object : overTextWatch() {
            override fun onTextChanged(write: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkPasswordEmptyorNo = write?.isNotEmpty()?: false
                emptyOrNo()
            }
        })

        checkBoxAgree.setOnCheckedChangeListener { _, _ ->
            emptyOrNo()
        }

        buttonOk.setOnClickListener {
            starting()
        }
    }
    private fun find() {
        textView = findViewById(R.id.textView)
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        checkBoxAgree = findViewById(R.id.checkAgreement)
        buttonOk = findViewById(R.id.buttonOk)
        progressBar = findViewById(R.id.loadingBar)
    }

    abstract class overTextWatch : android.text.TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
    }
    private fun emptyOrNo(){
        checkEmailEmptyorNo && checkPasswordEmptyorNo && checkBoxAgree.isChecked
    }

    private fun starting() {
        progressBar.visibility = View.VISIBLE
        inputEmail.isEnabled = false
        inputPassword.isEnabled = false
        checkBoxAgree.isEnabled = false
        buttonOk.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.GONE
            inputEmail.isEnabled = true
            inputPassword.isEnabled = true
            checkBoxAgree.isEnabled = true
            buttonOk.isEnabled = true
            Toast.makeText(
                this,
                resources.getString(R.string.loadText),
                Toast.LENGTH_LONG
            ).show()
        },2000)
    }
}