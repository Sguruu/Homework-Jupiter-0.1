package com.example.myapplication
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentShowerListFriendsBinding
import com.example.myapplication.databinding.ShowerListBinding

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
//        createItemsView()
        initList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createItemsView() {
        if (filterFriendList.isEmpty()){
            for (i in 0 until friendList.size){

                val view = ShowerListBinding.inflate(
                    layoutInflater,
                    binding.showerListLinearLayout,
                    false)
                view.apply {
                    view.friendName.text = friendList[i].name
                    view.friendSurname.text = friendList[i].surname
                    view.friendPhoneNumber.text = friendList[i].phoneNumber

                    view.buttonDellFriend.setOnClickListener {
                        for (i in 0 until friendList.size) {
                            if (friendList[i].name == view.friendName.text &&
                                friendList[i].surname == view.friendSurname.text &&
                                friendList[i].phoneNumber == view.friendPhoneNumber.text
                            ) {
                                friendList.remove(friendList[i])
                                break
                            }
                        }
                        binding.showerListLinearLayout.removeView(view.root)
                    }
                }
                binding.showerListLinearLayout.addView(view.root)
            }
        } else{
            for (i in 0 until filterFriendList.size){
                val view = ShowerListBinding.inflate(
                    layoutInflater,
                    binding.showerListLinearLayout,
                    false)
                view.apply {
                    view.friendName.text = filterFriendList[i].name
                    view.friendSurname.text = filterFriendList[i].surname
                    view.friendPhoneNumber.text = filterFriendList[i].phoneNumber

                    view.buttonDellFriend.setOnClickListener {
                        for (i in 0 until friendList.size) {
                            if (friendList[i].name == view.friendName.text &&
                                friendList[i].surname == view.friendSurname.text &&
                                friendList[i].phoneNumber == view.friendPhoneNumber.text
                            ) {
                                friendList.remove(friendList[i])
                                break
                            }
                        }
                        binding.showerListLinearLayout.removeView(view.root)
                    }
                }
                binding.showerListLinearLayout.addView(view.root)
            }
        }
    }
    private fun initList(){
        if (filterFriendList.isEmpty()){
            adapter = FriendAdapter(friendList)
            binding.recyclerView.adapter = FriendAdapter(friendList)
        }else{
            adapter = FriendAdapter(filterFriendList)
            binding.recyclerView.adapter = FriendAdapter(filterFriendList)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }
}