package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.TownViewModel
import com.example.myapplication.databinding.FragmentShowerTownsBinding
import com.example.myapplication.models.Town

class FragmentShowerTowns : Fragment() {

    private var _binding : FragmentShowerTownsBinding? = null
    private val binding get() = _binding!!
    private var adapter: TownAdapter? = null
    private val viewModel: TownViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowerTownsBinding.inflate(inflater, container, false)
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
//            if (viewModel.filterListLiveData.value == null ||
//                viewModel.filterListLiveData.value == ArrayList<Friend>())
                viewModel.townLiveData.value?: ArrayList()
//            else viewModel.filterListLiveData.value?: ArrayList()

        adapter = TownAdapter(
            useFriendList)
        { position, town ->
            adapter?.let {
                dellFriend(position, town)
            }
        }
        binding.townRecyclerView.adapter = adapter
        binding.townRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initObserve() {
        viewModel.townLiveData.observe(requireActivity()) {
            adapter?.updateTowns(it)
        }
//        viewModel.filterListLiveData.observe(requireActivity()) {
//            adapter?.updateList(it)
//        }
    }


    private fun dellFriend (position: Int, town: Town){
        val toastText = getString(R.string.dell_town_message, town.name)
        adapter?.let {
            it.notifyItemRemoved(position)
            viewModel.dellTown(town)
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    }
}