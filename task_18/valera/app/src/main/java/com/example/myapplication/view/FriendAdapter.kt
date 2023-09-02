package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.models.Friend
import com.example.myapplication.R

class FriendAdapter (
    private var friendList: ArrayList<Friend>,
    private val onButtonDeleteClickListener: (position: Int, friend: Friend) -> Unit
        ): RecyclerView.Adapter<FriendAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.shower_list, parent, false)
        return Holder(view, onButtonDeleteClickListener)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(friendList[position])
    }

    fun updateList (newFriendList: ArrayList<Friend>){
        friendList = newFriendList
    }

    class Holder (view: View,
                  private val onButtonDeleteClickListener: (position: Int, friend: Friend) -> Unit)
        : RecyclerView.ViewHolder (view){
        private val friendName: TextView = view.findViewById(R.id.friend_name)
        private val friendSurname: TextView = view.findViewById(R.id.friend_surname)
        private val friendPhoneNumber: TextView = view.findViewById(R.id.friend_phone_number)
        private val friendTown: TextView = view.findViewById(R.id.friend_town)
        private val dellFriendButton: Button = view.findViewById(R.id.button_dell_friend)

        fun bind(friend: Friend){
            friendName.text = friend.name
            friendSurname.text = friend.surname
            friendPhoneNumber.text = friend.phoneNumber
            friendTown.text = friend.town.name
            dellFriendButton.setOnClickListener {
                onButtonDeleteClickListener.invoke(adapterPosition, friend)
            }
        }
    }
}