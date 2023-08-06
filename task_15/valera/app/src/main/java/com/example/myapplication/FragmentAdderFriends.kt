package com.example.myapplication

import android.os.Build
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
import com.example.myapplication.databinding.FragmentAdderFriendsBinding

class FragmentAdderFriends : Fragment() {

    companion object{
        private const val KEY_TEXT = "KEY_TEXT"
        fun newInstance(friendList : ArrayList<Friend>) : Fragment{
            val args = Bundle()
            args.putParcelableArrayList(KEY_TEXT, friendList)

            val fragment = FragmentAdderFriends()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding : FragmentAdderFriendsBinding? = null
    private val binding get() = _binding!!
    private var checkEditTextNameNoEmpty = false
    private var checkEditTextSurnameNoEmpty = false
    private var checkEditTextPhoneNumberNoEmpty = false
    private lateinit var friendList : ArrayList<Friend>


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
        friendList =
        if (arguments!=null && Build.VERSION.SDK_INT >= 33){
            arguments?.getParcelableArrayList(KEY_TEXT, Friend::class.java)!!
        } else if (arguments!=null && Build.VERSION.SDK_INT < 33){
            arguments?.getParcelableArrayList(KEY_TEXT)!!
        } else ArrayList()
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
            addFriendOnList()
            finishProgressBar()
        }
    }

    private fun startProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.editName.isEnabled = false
        binding.edinSurname.isEnabled = false
        binding.editPhoneNumber.isEnabled = false
        binding.adderButtom.isEnabled = false
    }

    private fun addFriendOnList() {
        friendList.add(
            Friend(
                binding.editName.text.toString(),
                binding.edinSurname.text.toString(),
                binding.editPhoneNumber.text.toString()
            )
        )
    }

    private fun renderButtonEnabled() {
        binding.adderButtom.isEnabled =
            checkEditTextNameNoEmpty && checkEditTextSurnameNoEmpty && checkEditTextPhoneNumberNoEmpty
    }

    private fun finishProgressBar() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.editName.isEnabled = true
            binding.editName.text = null
            binding.edinSurname.isEnabled = true
            binding.edinSurname.text = null
            binding.editPhoneNumber.isEnabled = true
            binding.editPhoneNumber.text = null

            Toast.makeText(
                context,
                resources.getString(R.string.successfully),
                Toast.LENGTH_SHORT
            ).show()
        }, 2000)
    }
}