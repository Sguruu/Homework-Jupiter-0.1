package com.example.myapplication.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAdderTownBinding
import com.example.myapplication.models.Weather
import com.example.myapplication.viewmodels.InfoViewModel
import com.example.myapplication.viewmodels.TownViewModel

class FragmentAdderTown : Fragment() {
    private var _binding: FragmentAdderTownBinding? = null
    private val binding get() = _binding!!

    private var checkEditTextTownNameNoEmpty = false
    private var checkEditTextLatitudeNoEmpty = false
    private var checkEditTextLongitudeNoEmpty = false
    private val viewModel: TownViewModel by activityViewModels()
    private val weatherViewModel: InfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdderTownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherViewModel.infoLiveData.observe(requireActivity()) {
            Log.d("MyTest", "$it")
        }
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.editTownName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextTownNameNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.editLatitude.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextLatitudeNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.editLongitude.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEditTextLongitudeNoEmpty = p0?.isNotEmpty() ?: false
                renderButtonEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.adderTownButton.setOnClickListener {
            startProgressBar()
            weatherViewModel.requestWeather(
                binding.editLatitude.text.toString(),
                binding.editLongitude.text.toString()
            )
            finishProgressBar()
        }

        weatherViewModel.infoLiveData.observe(viewLifecycleOwner) {
            it?.let {
                val weather = Weather(
                    it.main.temp,
                    it.main.humidity,
                    it.main.description
                )

                viewModel.addTownOnList(
                    binding.editTownName.text.toString(),
                    binding.editLatitude.text.toString().toDouble(),
                    binding.editLongitude.text.toString().toDouble(),
                    weather
                )
            }
        }
    }

    private fun startProgressBar() {
        binding.progressBarOnAdderTown.visibility = View.VISIBLE
        binding.adderTownButton.isEnabled = false
        switchEditTexts(false)
    }

    private fun renderButtonEnabled() {
        binding.adderTownButton.isEnabled =
            checkEditTextTownNameNoEmpty && checkEditTextLatitudeNoEmpty && checkEditTextLongitudeNoEmpty
    }

    private fun finishProgressBar() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBarOnAdderTown.visibility = View.GONE
            binding.editTownName.text = null
            binding.editLatitude.text = null
            binding.editLongitude.text = null
            switchEditTexts(true)

            Toast.makeText(
                context,
                resources.getString(R.string.successfully_add_town),
                Toast.LENGTH_SHORT
            ).show()
        }, 2000)
    }

    /** enter true to on edit texts, enter false to off it */
    private fun switchEditTexts(onOrOff: Boolean) {
        binding.editTownName.isEnabled = onOrOff
        binding.editLatitude.isEnabled = onOrOff
        binding.editLongitude.isEnabled = onOrOff
    }
}
