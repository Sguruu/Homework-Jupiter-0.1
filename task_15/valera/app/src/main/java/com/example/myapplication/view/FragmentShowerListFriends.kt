package com.example.myapplication.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Friend
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentShowerListFriendsBinding

class FragmentShowerListFriends : Fragment() {

    private var _binding : FragmentShowerListFriendsBinding? = null
    private val binding get() = _binding!!
    private var adapter: FriendAdapter? = null
    private val viewModel: MainViewModel by activityViewModels()

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

    private fun initList(){
        val useFriendList =
        if (viewModel.filterListLiveData.value == null ||
            viewModel.filterListLiveData.value == ArrayList<Friend>())
            viewModel.friendLiveData.value?: ArrayList()
        else viewModel.filterListLiveData.value?: ArrayList()

        adapter = FriendAdapter(
            useFriendList)
         { position, friend ->
            adapter?.let {
                dellFriend(position, friend)
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initObserve() {
        viewModel.friendLiveData.observe(requireActivity()) {
            adapter?.updateList(it)
        }
        viewModel.filterListLiveData.observe(requireActivity()) {
            adapter?.updateList(it)
        }
    }


    private fun dellFriend (position: Int, friend: Friend){
        val toastText = "${friend.name} ${friend.surname} ${getString(R.string.dell_message)}"
        adapter?.let {
            it.notifyItemRemoved(position)
           viewModel.dellFriend(friend)
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    }
}