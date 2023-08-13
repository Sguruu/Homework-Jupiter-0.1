package com.weather.task7_3notebook.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.weather.task7_3notebook.R
import com.weather.task7_3notebook.databinding.FragmentAddCityBinding
import com.weather.task7_3notebook.model.City
import com.weather.task7_3notebook.model.StateStatusSaveCity
import com.weather.task7_3notebook.view.extensions.checkShowError
import com.weather.task7_3notebook.view.extensions.showError
import com.weather.task7_3notebook.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddCityFragment : Fragment() {
    private var _binding: FragmentAddCityBinding? = null
    private val binding get() = _binding!!
    private lateinit var toast: Toast
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toast = Toast(requireContext())
        initListener()
        initObserve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.stateStatusSaveCityFlow.onEach {
                when (it) {
                    is StateStatusSaveCity.Success -> {
                        clearEditText()
                        toast(resources.getString(R.string.save_city))
                    }

                    is StateStatusSaveCity.Error -> {
                        binding.editTextLat.showError(it.message)
                        binding.editTextLon.showError(it.message)
                        toast(it.message)
                    }

                    is StateStatusSaveCity.NoInternet -> {
                        clearEditText()
                        toast(resources.getString(R.string.error_no_internet))
                    }
                }
            }.collect()
        }
    }

    private fun initListener() {
        with(binding) {
            buttonAddCity.setOnClickListener {
                val nameCity = editTextNameCity.text.toString()
                val lat = editTextLat.text.toString()
                val lon = editTextLon.text.toString()

                editTextNameCity.checkShowError()
                editTextLat.checkShowError()
                editTextLon.checkShowError()

                if (nameCity.isEmpty() || lat.isEmpty() || lon.isEmpty()) {
                    toast(resources.getString(R.string.error_empty_edit_text_toast))
                } else {
                    mainViewModel.addCity(
                        City(
                            nameCity = nameCity,
                            latitude = lat,
                            longitude = lon
                        ),
                        requireContext()
                    )
                    renderProgressBar()
                }
            }
        }
    }

    private fun clearEditText() {
        binding.editTextNameCity.setText("")
        binding.editTextLat.setText("")
        binding.editTextLon.setText("")
    }

    private fun renderProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.buttonAddCity.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.buttonAddCity.isEnabled = true
        }, 2000)
    }

    private fun toast(text: String) {
        toast.cancel()
        toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
        toast.show()
    }
}
