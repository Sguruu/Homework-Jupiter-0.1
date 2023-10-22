package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Town


class TownAdapter(
    private var towns: ArrayList<Town>,
    private val onButtonDeleteClickListener: (position: Int, town: Town) -> Unit,
    private val dataChange: ((position: Int) -> Unit)? = null
) : RecyclerView.Adapter<TownAdapter.TownHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TownHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.town_shower_list, parent, false)
        return TownHolder(view, onButtonDeleteClickListener, dataChange)
    }

    override fun getItemCount(): Int {
        return towns.size
    }

    override fun onBindViewHolder(holder: TownHolder, position: Int) {
        holder.bind(towns[position])
    }

    fun updateTowns(newTowns: ArrayList<Town>) {
        towns = newTowns
    }

    class TownHolder(
        view: View,
        private val onButtonDeleteClickListener: (position: Int, town: Town) -> Unit,
        dataChange: ((position: Int) -> Unit)? = null
    ) : RecyclerView.ViewHolder(view) {
        private val townName: TextView = view.findViewById(R.id.town_name)
        private val coordinates: TextView = view.findViewById(R.id.coordinates)
        private val temp: TextView = view.findViewById(R.id.temp)
        private val humidity: TextView = view.findViewById(R.id.humidity)
        private val description: TextView = view.findViewById(R.id.description)
        private val dellTownButton: Button = view.findViewById(R.id.button_dell_town)

        init {
            view.setOnClickListener {
                dataChange?.invoke(adapterPosition)
            }
        }

        fun bind(town: Town) {
            townName.text = town.name
            coordinates.text = itemView.resources.getString(
                R.string.town_coordinates_completion,
                town.latitude.toString(),
                town.longitude.toString()
            )

            if (town.weather != null) {
                temp.text = itemView.resources.getString(
                    R.string.temperature,
                    town.weather!!.tempValue.toString()
                )
                humidity.text = itemView.resources.getString(
                    R.string.humidity,
                    town.weather!!.humidityValue.toString()
                ) + "%"
                description.text = town.weather!!.description
            }
            dellTownButton.setOnClickListener {
                onButtonDeleteClickListener.invoke(adapterPosition, town)
            }
        }
    }
}