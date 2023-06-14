package com.weather.task7_3notebook

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.weather.task7_3notebook.databinding.FragmentAddPersonBinding

class AddPersonFragment : Fragment() {
    private var _binding: FragmentAddPersonBinding? = null
    private val binding get() = _binding!!
    private lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toast = Toast(requireContext())
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListener() {
        binding.buttonAddContact.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val numberPhone = binding.editTextNumber.text.toString().toIntOrNull() ?: 0

            binding.editTextName.setText("")
            binding.editTextLastName.setText("")
            binding.editTextNumber.setText("")

            (activity as IMainActivity).addContact(Contact(name, lastName, numberPhone))
            renderProgressBar(resources.getString(R.string.save_contact))
        }
    }

    private fun renderProgressBar(text: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.buttonAddContact.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.buttonAddContact.isEnabled = true
            toast(text)
        }, 2000)
    }

    private fun toast(text: String) {
        toast.cancel()
        toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
        toast.show()
    }

    companion object {
        const val FRAGMENT_TAG = "AddPersonFragment"
    }
}
