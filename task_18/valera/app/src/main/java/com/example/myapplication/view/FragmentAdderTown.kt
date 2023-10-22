package com.example.myapplication.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAdderTownBinding
import com.example.myapplication.models.Weather
import com.example.myapplication.view_models.InfoViewModel
import com.example.myapplication.view_models.TownViewModel


class FragmentAdderTown : Fragment() {
    private var _binding: FragmentAdderTownBinding? = null
    private val binding get() = _binding!!

    private var checkEditTextTownNameNoEmpty = false
    private var checkEditTextLatitudeNoEmpty = false
    private var checkEditTextLongitudeNoEmpty = false
    private val townViewModel: TownViewModel by activityViewModels()
    private val infoViewModel: InfoViewModel by activityViewModels()

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
        initListeners(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners(savedInstanceState: Bundle?) {
        var savedInstanceStateValue = savedInstanceState
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
            savedInstanceStateValue = null
            startProgressBar()

            if (infoViewModel.positionLiveData.value != null) {
                val index = infoViewModel.positionLiveData.value!!
                if (townViewModel.checkIsInternet(requireContext())) {
                    infoViewModel.requestWeather(
                        binding.editLatitude.text.toString(),
                        binding.editLongitude.text.toString()
                    )
                } else {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.no_internet_message_for_change_town),
                        Toast.LENGTH_SHORT
                    ).show()
                    it?.let {
                        townViewModel.townLiveData.value!![index].name =
                            binding.editTownName.text.toString()
                        townViewModel.townLiveData.value!![index].latitude =
                            binding.editLatitude.text.toString().toDouble()
                        townViewModel.townLiveData.value!![index].longitude =
                            binding.editLongitude.text.toString().toDouble()
                    }
                    infoViewModel.updatePositionLiveData(null)
                    finishProgressBar()
                }
            } else {
                if (townViewModel.checkIsInternet(requireContext())) {
                    infoViewModel.requestWeather(
                        binding.editLatitude.text.toString(),
                        binding.editLongitude.text.toString()
                    )
                } else {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.no_internet_message),
                        Toast.LENGTH_SHORT
                    ).show()
                    it?.let {
                        townViewModel.addTownOnList(
                            binding.editTownName.text.toString(),
                            binding.editLatitude.text.toString().toDouble(),
                            binding.editLongitude.text.toString().toDouble(),
                            null
                        )
                    }
                    finishProgressBar()
                }
            }

        }

        infoViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            if (binding.editTownName.text.isNotEmpty()
                && binding.editLatitude.text.isNotEmpty()
                && binding.editLongitude.text.isNotEmpty()
                && savedInstanceStateValue == null
                && infoViewModel.weatherLiveData.value?.main != null
            ) {
                if (infoViewModel.positionLiveData.value != null) {
                    val index = infoViewModel.positionLiveData.value!!
                    it?.let {
                        val weather = Weather(
                            it.main!!.temp,
                            it.main!!.humidity,
                            it.main!!.description
                        )
                        townViewModel.townLiveData.value!![index].name =
                            binding.editTownName.text.toString()
                        townViewModel.townLiveData.value!![index].latitude =
                            binding.editLatitude.text.toString().toDouble()
                        townViewModel.townLiveData.value!![index].longitude =
                            binding.editLongitude.text.toString().toDouble()
                        townViewModel.townLiveData.value!![index].weather = weather
                    }
                    infoViewModel.updatePositionLiveData(null)
                } else {
                    it?.let {
                        val weather = Weather(
                            it.main!!.temp,
                            it.main!!.humidity,
                            it.main!!.description
                        )
                        townViewModel.addTownOnList(
                            binding.editTownName.text.toString(),
                            binding.editLatitude.text.toString().toDouble(),
                            binding.editLongitude.text.toString().toDouble(),
                            weather
                        )
                    }
                }
                finishProgressBar()
            }
            if (infoViewModel.weatherLiveData.value?.main == null) {
                Toast.makeText(context, R.string.error_server_response, Toast.LENGTH_SHORT).show()
                binding.progressBarOnAdderTown.visibility = View.GONE
                binding.adderTownButton.isEnabled = true
                switchEditTexts(true)
            }
        }

        infoViewModel.positionLiveData.observe(viewLifecycleOwner) {
            val position = infoViewModel.positionLiveData.value
            if (position != null &&
                townViewModel.townLiveData.value != null
            ) {
                val town = townViewModel.townLiveData.value!![position]
                binding.editTownName.setText(town.name)
                binding.editLatitude.setText(town.latitude.toString())
                binding.editLongitude.setText(town.longitude.toString())
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
    }

    /** enter true to on edit texts, enter false to off it */
    private fun switchEditTexts(onOrOff: Boolean) {
        binding.editTownName.isEnabled = onOrOff
        binding.editLatitude.isEnabled = onOrOff
        binding.editLongitude.isEnabled = onOrOff
    }
}
