package com.example.myapplication.view

import com.example.myapplication.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.view_models.FriendViewModel
import com.example.myapplication.view_models.TownViewModel
import com.example.myapplication.databinding.FragmentAdderFriendsBinding
import com.example.myapplication.models.Town
import com.example.myapplication.view_models.InfoViewModel


class FragmentAdderFriends : Fragment() {

    private var _binding: FragmentAdderFriendsBinding? = null
    private val binding get() = _binding!!
    private var checkEditTextNameNoEmpty = false
    private var checkEditTextSurnameNoEmpty = false
    private var checkEditTextPhoneNumberNoEmpty = false
    private val friendViewModel: FriendViewModel by activityViewModels()
    private val townViewModel: TownViewModel by activityViewModels()
    private val infoViewModel: InfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdderFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObserve()
        binding.spinner.setSpinnerFocusable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
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
            startProgressBar()
            if (infoViewModel.positionLiveData.value != null) {
                val index = infoViewModel.positionLiveData.value!!
                if (binding.spinner.selectedItemPosition >= 0) {
                    val town =
                        townViewModel.townLiveData.value?.get(binding.spinner.selectedItemPosition)
                    friendViewModel.friendLiveData.value!![index].name =
                        binding.editName.text.toString()
                    friendViewModel.friendLiveData.value!![index].surname =
                        binding.edinSurname.text.toString()
                    friendViewModel.friendLiveData.value!![index].phoneNumber =
                        binding.editPhoneNumber.text.toString()
                    friendViewModel.friendLiveData.value!![index].town = town!!
                } else {
                    friendViewModel.friendLiveData.value!![index].name =
                        binding.editName.text.toString()
                    friendViewModel.friendLiveData.value!![index].surname =
                        binding.edinSurname.text.toString()
                    friendViewModel.friendLiveData.value!![index].phoneNumber =
                        binding.editPhoneNumber.text.toString()
                    friendViewModel.friendLiveData.value!![index].town =
                        Town(R.string.default_town_name.toString(), 0.0, 0.0)
                }
                infoViewModel.updatePositionLiveData(null)
            } else {
                if (binding.spinner.selectedItemPosition >= 0) {
                    val town =
                        townViewModel.townLiveData.value?.get(binding.spinner.selectedItemPosition)
                    friendViewModel.addFriendOnList(
                        binding.editName.text.toString(),
                        binding.edinSurname.text.toString(),
                        binding.editPhoneNumber.text.toString(),
                        town!!
                    )
                } else {
                    friendViewModel.addFriendOnList(
                        binding.editName.text.toString(),
                        binding.edinSurname.text.toString(),
                        binding.editPhoneNumber.text.toString(),
                        Town(R.string.default_town_name.toString(), 0.0, 0.0)
                    )
                }
            }
            finishProgressBar()
        }
    }

    private fun startProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.adderButtom.isEnabled = false
        switchEditTexts(false)
    }

    private fun renderButtonEnabled() {
        binding.adderButtom.isEnabled =
            checkEditTextNameNoEmpty && checkEditTextSurnameNoEmpty && checkEditTextPhoneNumberNoEmpty
    }

    private fun finishProgressBar() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.editName.text = null
            binding.edinSurname.text = null
            binding.editPhoneNumber.text = null
            switchEditTexts(true)

            Toast.makeText(
                context,
                resources.getString(R.string.successfully),
                Toast.LENGTH_SHORT
            ).show()
        }, 2000)
    }

    /** enter true to on edit texts, enter false to off it */
    private fun switchEditTexts(onOrOff: Boolean) {
        binding.editName.isEnabled = onOrOff
        binding.edinSurname.isEnabled = onOrOff
        binding.editPhoneNumber.isEnabled = onOrOff
    }

    private fun AppCompatSpinner.setSpinnerFocusable() {
        isFocusableInTouchMode = true
        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (windowToken != null) {
                    performClick()

                    viewTreeObserver?.addOnWindowFocusChangeListener(object :
                        ViewTreeObserver.OnWindowFocusChangeListener {
                        override fun onWindowFocusChanged(hasFocus: Boolean) {
                            if (hasFocus) {
                                clearFocus()
                                viewTreeObserver?.removeOnWindowFocusChangeListener(this)
                            }
                        }
                    })
                }
            }
        }
    }

    private fun initObserve() {
        townViewModel.townLiveData.observe(viewLifecycleOwner) {
            updateAdapterSpinner(it)
        }

        infoViewModel.positionLiveData.observe(viewLifecycleOwner) {

            val position = infoViewModel.positionLiveData.value

            if (position != null &&
                friendViewModel.friendLiveData.value != null
            ) {
                val friend = friendViewModel.friendLiveData.value!![position]
                binding.editName.setText(friend.name)
                binding.edinSurname.setText(friend.surname)
                binding.editPhoneNumber.setText(friend.phoneNumber)
            }
        }
    }

    private fun updateAdapterSpinner(towns: ArrayList<Town>) {
        val listTownName = towns.map { it.name }
        binding.spinner.adapter =
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                listTownName
            )
    }
}