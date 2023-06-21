package com.example.myapplication

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val notFoundAppMessage = "App for call not found"
    private val callCompletedMessage = "Call is completed"
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            Toast.makeText(this, callCompletedMessage, Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOpenCallManager.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL).apply {
                val enterPhoneNumber = binding.editPhoneNumber.text.toString()
                data = Uri.parse("tel:$enterPhoneNumber")
            }
            try {
                resultLauncher.launch(callIntent)
            } catch (e: ActivityNotFoundException){
                Toast.makeText(this, notFoundAppMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}