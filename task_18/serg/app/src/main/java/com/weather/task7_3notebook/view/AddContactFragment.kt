package com.weather.task7_3notebook.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.weather.task7_3notebook.R
import com.weather.task7_3notebook.databinding.FragmentAddContactBinding
import com.weather.task7_3notebook.model.City
import com.weather.task7_3notebook.model.Contact
import com.weather.task7_3notebook.view.extensions.setSpinnerFocusable
import com.weather.task7_3notebook.viewmodel.MainViewModel

class AddContactFragment : Fragment() {
    private var _binding: FragmentAddContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var toast: Toast
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toast = Toast(requireContext())
        initView()
        initListener()
        initObserve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.spinner.setSpinnerFocusable()
    }

    private fun initListener() {
        binding.buttonAddContact.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val numberPhone = binding.editTextNumber.text.toString().toIntOrNull() ?: 0

            binding.editTextName.setText("")
            binding.editTextLastName.setText("")
            binding.editTextNumber.setText("")

            mainViewModel.addContact(
                Contact(
                    name,
                    lastName,
                    numberPhone,
                    mainViewModel.cityLiveData.value?.get(binding.spinner.selectedItemPosition)
                )
            )
            renderProgressBar(resources.getString(R.string.save_contact))
        }
    }

    private fun initObserve() {
        mainViewModel.cityLiveData.observe(viewLifecycleOwner) {
            updateAdapterSpinner(it)
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

    private fun updateAdapterSpinner(list: List<City>) {
        val listCityName = list.map { it.nameCity }
        binding.spinner.adapter =
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                listCityName
            )
    }

    private fun toast(text: String) {
        toast.cancel()
        toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
        toast.show()
    }
}
