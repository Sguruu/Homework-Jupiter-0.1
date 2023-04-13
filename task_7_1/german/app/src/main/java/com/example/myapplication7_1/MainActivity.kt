package com.example.myapplication7_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication7_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var checkBoxAgree: CheckBox
    lateinit var buttonOk: Button
    lateinit var progressBar: ProgressBar

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

    fun name() {
        lateinit var textView: TextView
        lateinit var inputEmail: EditText
        lateinit var inputPassword: EditText
        lateinit var checkBoxAgree: CheckBox
        lateinit var buttonOk: Button
        lateinit var progressBar: ProgressBar
    }
    fun find() {
        textView = findViewById<TextView>(R.id.textView)
        inputEmail = findViewById<EditText>(R.id.editEmail)
        inputPassword = findViewById<EditText>(R.id.editPassword)
        checkBoxAgree = findViewById<CheckBox>(R.id.agreeCheck)
        buttonOk = findViewById<Button>(R.id.buttonOk)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
    }
    abstract class overTextWatch : android.text.TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
    }
    private fun emptyOrNo(){
        buttonOk.isEnabled =
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
                resources.getString(R.string.progress),
                Toast.LENGTH_LONG
            ).show()
        },2000)
    }
}
