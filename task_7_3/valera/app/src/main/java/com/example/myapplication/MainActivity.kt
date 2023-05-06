package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var checkEditTextNameNoEmpty = false
    private var checkEditTextSurnameNoEmpty = false
    private var checkEditTextPhoneNumberNoEmpty = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
    }
    fun initToolbar(){
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId){

                R.id.searcher->{true}

                R.id.adder ->{
                    binding.welcomeWords.visibility = View.GONE
                    binding.adderScroll.visibility = View.VISIBLE
                    adder_friend()

                    true}

                R.id.shower->{true}

                R.id.joke_listener->{true}

                else ->{false}
            }
        }
    }
    fun adder_friend(){

        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextNameNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.edinSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextSurnameNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.editPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextPhoneNumberNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.adderButtom.setOnClickListener {
            startWheel()

        }
    }

    private fun startWheel() {
        binding.wheel.visibility = View.VISIBLE
        binding.editName.isEnabled = false
        binding.edinSurname.isEnabled = false
        binding.editPhoneNumber.isEnabled = false
        binding.adderButtom.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            binding.wheel.visibility = View.GONE
            binding.editName.isEnabled = true
            binding.editName.text = null
            binding.edinSurname.isEnabled = true
            binding.edinSurname.text = null
            binding.editPhoneNumber.isEnabled = true
            binding.editPhoneNumber.text = null
            Toast.makeText(
                this,
                resources.getString(R.string.successfully),
                Toast.LENGTH_SHORT
            ).show()
        }, 2000)
    }

    private fun renderButtonEnabled() {
        binding.adderButtom.isEnabled =
            checkEditTextNameNoEmpty && checkEditTextSurnameNoEmpty && checkEditTextPhoneNumberNoEmpty
    }
}