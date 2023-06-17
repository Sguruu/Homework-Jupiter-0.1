package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
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
                startActivity(callIntent)
            } catch (e: ActivityNotFoundException){
                Toast.makeText(this, "App for call not found", Toast.LENGTH_SHORT).show()
            }

        }
    }
}