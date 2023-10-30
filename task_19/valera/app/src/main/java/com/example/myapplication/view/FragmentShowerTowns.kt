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
import com.example.myapplication.R
import com.example.myapplication.view_models.TownViewModel
import com.example.myapplication.databinding.FragmentShowerTownsBinding
import com.example.myapplication.models.Town
import com.example.myapplication.view_models.InfoViewModel

class FragmentShowerTowns : Fragment() {

    private var _binding: FragmentShowerTownsBinding? = null
    private val binding get() = _binding!!
    private var adapter: TownAdapter? = null
    private val townViewModel: TownViewModel by activityViewModels()
    private val infoViewModel: InfoViewModel by activityViewModels()

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

    private fun initList() {
        val towns =
            if (townViewModel.filterListLiveData.value == null ||
                townViewModel.filterListLiveData.value == ArrayList<Town>()
            )
                townViewModel.townLiveData.value ?: ArrayList()
            else townViewModel.filterListLiveData.value ?: ArrayList()


        adapter = TownAdapter(
            towns,
            { position, town -> adapter?.let { deleteTown(position, town) } },
            { position ->
                infoViewModel.updatePositionLiveData(position)
                findNavController().navigate(R.id.action_global_fragmentAdderTown)
            }
        )
        binding.townRecyclerView.adapter = adapter
        binding.townRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initObserve() {
        townViewModel.townLiveData.observe(requireActivity()) {
            adapter?.updateTowns(it)
        }
        townViewModel.filterListLiveData.observe(requireActivity()) {
            adapter?.updateTowns(it)
        }
    }


    private fun deleteTown(position: Int, town: Town) {
        val toastText = getString(R.string.dell_town_message, town.name)
        adapter?.let {
            it.notifyItemRemoved(position)
            townViewModel.dellTown(town)
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    }
}