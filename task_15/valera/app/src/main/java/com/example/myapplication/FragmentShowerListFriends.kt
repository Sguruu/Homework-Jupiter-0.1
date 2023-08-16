package com.example.myapplication
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentShowerListFriendsBinding

class FragmentShowerListFriends : Fragment() {

    companion object{
        private const val KEY_filter_friendList = "KEY_TEXT1"
        private const val KEY_friendList = "KEY_TEXT2"
        fun newInstance(filterFriendList : ArrayList<Friend>, friendList : ArrayList<Friend>)
        : Fragment{
            val args = Bundle()
            args.putParcelableArrayList(KEY_filter_friendList, filterFriendList)
            args.putParcelableArrayList(KEY_friendList, friendList)

            val fragment = FragmentShowerListFriends()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding : FragmentShowerListFriendsBinding? = null
    private val binding get() = _binding!!
    private lateinit var friendList : ArrayList<Friend>
    private lateinit var filterFriendList : ArrayList<Friend>
    private var adapter: FriendAdapter? = null

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

            if (arguments!=null && Build.VERSION.SDK_INT >= 33){
                friendList = arguments?.getParcelableArrayList(KEY_friendList, Friend::class.java)!!
                filterFriendList = arguments?.getParcelableArrayList(
                    KEY_filter_friendList, Friend::class.java)!!
            } else if (arguments!=null && Build.VERSION.SDK_INT < 33){
                friendList = arguments?.getParcelableArrayList(KEY_friendList)!!
                filterFriendList = arguments?.getParcelableArrayList(KEY_filter_friendList)!!
            }
            else {
                friendList = ArrayList()
                filterFriendList = ArrayList()
            }
        initList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList(){
        val useFriendList =
        if (filterFriendList.isEmpty()) friendList
        else filterFriendList

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

    private fun dellFriend (position: Int, friend: Friend){
        val toastText = "${friend.name} ${friend.surname} ${getString(R.string.dell_message)}"
        adapter?.let {
            (activity as MainInterface).dellFriend(friend)
            it.dellFriend(friend)
            it.notifyItemRemoved(position)
            it.notifyDataSetChanged()
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    }
}