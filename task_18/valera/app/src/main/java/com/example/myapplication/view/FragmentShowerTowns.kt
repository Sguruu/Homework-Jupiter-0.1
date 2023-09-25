package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.view_models.TownViewModel
import com.example.myapplication.databinding.FragmentShowerTownsBinding
import com.example.myapplication.models.Town
import com.example.myapplication.view_models.InfoViewModel

class FragmentShowerTowns : Fragment() {

    private var _binding : FragmentShowerTownsBinding? = null
    private val binding get() = _binding!!
    private var adapter: TownAdapter? = null
    private val townViewModel: TownViewModel by activityViewModels()
    private val weatherViewModel: InfoViewModel by activityViewModels()

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
        initList(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList(savedInstanceState: Bundle?){
        val towns =
//            if (viewModel.filterListLiveData.value == null ||
//                viewModel.filterListLiveData.value == ArrayList<Friend>())
            townViewModel.townLiveData.value?: ArrayList()
//            else viewModel.filterListLiveData.value?: ArrayList()

//        if (savedInstanceState == null){
//            towns = weatherViewModel.setWeatherForTowns(towns)
//        }

        adapter = TownAdapter(
            towns)
        { position, town ->
            adapter?.let {
                dellTown(position, town)
            }
        }
        binding.townRecyclerView.adapter = adapter
        binding.townRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initObserve() {
        townViewModel.townLiveData.observe(requireActivity()) {
            adapter?.updateTowns(it)
        }
        weatherViewModel.infoLiveData.observe(viewLifecycleOwner){
            Log.d("MyTest", "$it")
        }
//        viewModel.filterListLiveData.observe(requireActivity()) {
//            adapter?.updateList(it)
//        }
    }


    private fun dellTown (position: Int, town: Town){
        val toastText = getString(R.string.dell_town_message, town.name)
        adapter?.let {
            it.notifyItemRemoved(position)
            townViewModel.dellTown(town)
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    }

//    fun setWeatherForTowns(towns: ArrayList<Town>): ArrayList<Town>{
//        towns.forEach {
//            weatherViewModel.requestWeather(it.latitude.toString(), it.longitude.toString())
//            it.weather?.tempValue = weatherViewModel.infoLiveData.value?.main?.temp?: 0.0
//            it.weather?.descriptionValue = weatherViewModel.infoLiveData.value?.main?.description?: "Нет данных"
//            it.weather?.humidityValue = weatherViewModel.infoLiveData.value?.main?.humidity?: 0
//        }
//        return towns
//    }
}