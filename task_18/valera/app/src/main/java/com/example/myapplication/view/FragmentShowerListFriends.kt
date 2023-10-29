package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.models.Friend
import com.example.myapplication.view_models.FriendViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentShowerListFriendsBinding
import com.example.myapplication.view_models.InfoViewModel

class FragmentShowerListFriends : Fragment() {

    private var _binding: FragmentShowerListFriendsBinding? = null
    private val binding get() = _binding!!
    private var adapter: FriendAdapter? = null
    private val friendViewModel: FriendViewModel by activityViewModels()
    private val infoViewModel: InfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowerListFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList() {
        val useFriendList =
            if (friendViewModel.filterListLiveData.value == null ||
                friendViewModel.filterListLiveData.value == ArrayList<Friend>()
            )
                friendViewModel.friendLiveData.value ?: ArrayList()
            else friendViewModel.filterListLiveData.value ?: ArrayList()

        adapter = FriendAdapter(
            useFriendList,
            { position, friend -> adapter?.let { dellFriend(position, friend) } },
            { position ->
                infoViewModel.updatePositionLiveData(position)
                findNavController().navigate(R.id.action_global_fragmentAdderFriends)
            })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initObserve() {
        friendViewModel.friendLiveData.observe(requireActivity()) {
            adapter?.updateList(it)
        }
        friendViewModel.filterListLiveData.observe(requireActivity()) {
            adapter?.updateList(it)
        }
    }


    private fun dellFriend(position: Int, friend: Friend) {
        val toastText = "${friend.name} ${friend.surname} ${getString(R.string.dell_message)}"
        adapter?.let {
            it.notifyItemRemoved(position)
            friendViewModel.deleteFriend(friend)
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    }
}