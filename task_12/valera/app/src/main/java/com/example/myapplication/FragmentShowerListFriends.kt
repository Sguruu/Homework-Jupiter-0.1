package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentShowerListFriendsBinding
import com.example.myapplication.databinding.ShowerListBinding

class FragmentShowerListFriends : Fragment() {

    companion object{
        private const val KEY_TEXT = "KEY_TEXT"
        fun newInstance(friendList : ArrayList<Friend>) : Fragment{
            val args = Bundle()
            args.putParcelableArrayList(KEY_TEXT, friendList)

            val fragment = FragmentShowerListFriends()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding : ShowerListBinding? = null
    private val binding get() = _binding!!
    private lateinit var friendList : ArrayList<Friend>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ShowerListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendList =
            if (arguments!=null) arguments?.getParcelableArrayList(KEY_TEXT)!!
            else {
                ArrayList<Friend>()
            }
        createItemsView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createItemsView() {
        for (i in 0 until friendList.size){
            val view = FragmentShowerListFriendsBinding.inflate(layoutInflater, binding.showList, false)
            view.apply {
                view.friendName.text = friendList[i].name
                view.friendSurname.text = friendList[i].surname
                view.friendPhoneNumber.text = friendList[i].phoneNumber

                view.buttomDellFriend.setOnClickListener {
                    for (i in 0 until friendList.size) {
                        if (friendList[i].name == view.friendName.text &&
                            friendList[i].surname == view.friendSurname.text &&
                            friendList[i].phoneNumber == view.friendPhoneNumber.text
                        ) {
                            friendList.remove(friendList[i])
                            break
                        }
                    }
                    binding.showList.removeView(view.root)
                }
            }
            binding.showList.addView(view.root)
        }
    }
}