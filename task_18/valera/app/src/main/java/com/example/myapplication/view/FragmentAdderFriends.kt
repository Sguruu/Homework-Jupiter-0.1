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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.viewModels.FriendViewModel
import com.example.myapplication.viewModels.TownViewModel
import com.example.myapplication.databinding.FragmentAdderFriendsBinding
import com.example.myapplication.models.Town


class FragmentAdderFriends : Fragment() {

    private var _binding : FragmentAdderFriendsBinding? = null
    private val binding get() = _binding!!
    private var checkEditTextNameNoEmpty = false
    private var checkEditTextSurnameNoEmpty = false
    private var checkEditTextPhoneNumberNoEmpty = false
    private val viewModel: FriendViewModel by activityViewModels()
    private val townViewModel: TownViewModel by activityViewModels()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners(){
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
            viewModel.addFriendOnList(
                binding.editName.text.toString(),
                binding.edinSurname.text.toString(),
                binding.editPhoneNumber.text.toString(),
                Town("Самара", 10.5, 15.7)
            )
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
    private fun switchEditTexts (onOrOff: Boolean){
        binding.editName.isEnabled = onOrOff
        binding.edinSurname.isEnabled = onOrOff
        binding.editPhoneNumber.isEnabled = onOrOff
    }
}