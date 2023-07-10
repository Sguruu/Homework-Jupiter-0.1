package com.weather.task7_3notebook.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.task7_3notebook.databinding.ItemCityBinding
import com.weather.task7_3notebook.databinding.ItemContactBinding
import com.weather.task7_3notebook.model.City

class CityAdapter(
    private val onItemDeleteClickListener: (city: City) -> Unit
) : RecyclerView.Adapter<CityAdapter.Holder>() {

    var contactList: List<City> = emptyList()
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflate)
        return Holder(binding, onItemDeleteClickListener)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(contactList[position])
    }

    /**
     * Обновить список
     */
    fun updateList(list: List<City>) {
        contactList = list
    }

    class Holder(
        private val binding: ItemCityBinding,
        private val onItemDeleteClickListener: (city: City) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            with(binding) {
                textViewCityName.text = city.nameCity
                textViewWeather.text = "Прогноз : полный Олег"
                textViewMinTemp.text = "Мин : 16.46 °C"
                textViewMaxTemp.text = "Макс : 16.46 °C"
                binding.buttonDelete.setOnClickListener {
                    onItemDeleteClickListener.invoke(city)
                }
            }
        }
    }
}
