package com.weather.t_9_1

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.weather.t_9_1.databinding.ActivityProfileBinding
import java.util.regex.Pattern

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("MyTest", "Звонок совершен")
            // проверяем на успешность завершения
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("MyTest", "Звонок совершен")
                // отмена звонка
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.d("MyTest", "Звонок отменен")
            } else {
                // другие результаты
                Log.d("MyTest", "Другое")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.buttonOpenContact.setOnClickListener {
            val isValidPhoneNumber = isValidPhoneNumber(binding.editText.text.toString())
            if (isValidPhoneNumber) {
                // если номер валидный
                val numberText = binding.editText.text.toString()
                binding.textViewError.text = ""
                openContactsApp(numberText)
            } else {
                // если номер не валидный
                binding.textViewError.text = resources.getString(R.string.error_phone)
            }
        }
    }

    private fun openContactsApp(numberText: String) {
        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$numberText")
            putExtra(Intent.EXTRA_DOCK_STATE, numberText)
        }
        try {
            activityLauncher.launch(phoneIntent)
        } catch (e: ActivityNotFoundException) {
            showToast("Нет нужного компонента")
        }
    }

    /**
     * Проверяет, соответствует ли строка номеру телефона в международном формате.
     * Если строка соответствует регулярному выражению, метод вернет true, иначе - false.
     */
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val regex = "^\\+(?:[0-9] ?){6,14}[0-9]$"
        return Pattern.matches(regex, phoneNumber)
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
