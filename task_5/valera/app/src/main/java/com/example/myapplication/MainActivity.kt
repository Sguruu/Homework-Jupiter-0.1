package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast


class MainActivity : AppCompatActivity() {


    lateinit var editTextLogin:EditText
    lateinit var editTextPassword:EditText
    lateinit var accept:CheckBox
    lateinit var buttonLogin:Button
    lateinit var wheel:ProgressBar
    var check1 = false
    var check2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextLogin = findViewById(R.id.edit_login)
        editTextPassword = findViewById(R.id.edit_password)
        accept = findViewById(R.id.accept)
        buttonLogin = findViewById(R.id.button_login)
        wheel = findViewById(R.id.wheel)


        editTextLogin.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                check1 = p0?.isNotEmpty()?: false
                buttonLogin.isEnabled = check1 && check2 && accept.isChecked
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        editTextPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                check2 = p0?.isNotEmpty()?: false
                buttonLogin.isEnabled = check1 && check2 && accept.isChecked
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        accept.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                buttonLogin.isEnabled = check1 && check2
            }else{
                buttonLogin.isEnabled = false
            }
        }


        buttonLogin.setOnClickListener{
            startWheel()
        }
    }

    private fun startWheel(){
     wheel.visibility = View.VISIBLE
        editTextLogin.isEnabled = false
        editTextPassword.isEnabled = false
        accept.isEnabled = false
        buttonLogin.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            wheel.visibility = View.GONE
            editTextLogin.isEnabled = true
            editTextPassword.isEnabled = true
            accept.isEnabled = true
            buttonLogin.isEnabled = true
            Toast.makeText(
                this,
                resources.getString(R.string.successfully),
                Toast.LENGTH_SHORT).show()
        },2000)
    }

}