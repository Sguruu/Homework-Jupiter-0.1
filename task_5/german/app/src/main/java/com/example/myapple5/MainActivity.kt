package com.example.myapple5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var text_view: TextView
    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var agreement: CheckBox
    lateinit var buttonDone: Button
    lateinit var loadingDone: ProgressBar

    private var emailEmptyOrNo = false
    private var passwordEmptyOrNo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find()

        inputEmail.addTextChangedListener(object : classWatcher() {
            override fun onTextChanged(write: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emailEmptyOrNo = write?.isNotEmpty()?:false
                emptyOrNo()
            }
        })
        inputPassword.addTextChangedListener(object: classWatcher(){
            override fun onTextChanged(write: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passwordEmptyOrNo = write?.isNotEmpty()?:false
                emptyOrNo()
            }
        })
        agreement.setOnCheckedChangeListener { _, _ ->
            emptyOrNo()
        }
        buttonDone.setOnClickListener {
            startProgressBar()
        }


    }
    private fun find(){
        text_view = findViewById(R.id.textView)
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        agreement = findViewById(R.id.checkAgreement)
        buttonDone = findViewById(R.id.buttonOk)
        loadingDone = findViewById(R.id.loadingBar)
    }

    abstract class classWatcher: android.text.TextWatcher{
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
    private fun emptyOrNo(){
        buttonDone.isEnabled = emailEmptyOrNo && passwordEmptyOrNo && agreement.isChecked
    }
    private fun startProgressBar(){
        loadingDone.visibility = View.VISIBLE
        inputPassword.isEnabled = false
        inputEmail.isEnabled = false
        agreement.isEnabled = false
        buttonDone.isEnabled = false


        Handler(Looper.getMainLooper()).postDelayed({
            loadingDone.visibility = View.GONE
            inputEmail.isEnabled = true
            inputPassword.isEnabled = true
            agreement.isEnabled = true
            buttonDone.isEnabled = true
            Toast.makeText(
                this,
                resources.getString(R.string.load),
                Toast.LENGTH_LONG
            ).show()
        },2000)
    }
}