package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Town


class TownAdapter (
    private var towns: ArrayList<Town>,
    private val onButtonDeleteClickListener: (position: Int, town: Town) -> Unit
): RecyclerView.Adapter<TownAdapter.TownHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TownHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.town_shower_list, parent, false)
        return TownHolder(view, onButtonDeleteClickListener)
    }

    override fun getItemCount(): Int {
        return towns.size
    }

    override fun onBindViewHolder(holder: TownHolder, position: Int) {
        holder.bind(towns[position])
    }

    fun updateTowns (newTowns: ArrayList<Town>){
        towns = newTowns
    }

    class TownHolder (view: View,
                  private val onButtonDeleteClickListener: (position: Int, town: Town) -> Unit)
        : RecyclerView.ViewHolder (view){
        private val townName: TextView = view.findViewById(R.id.town_name)
        private val coordinates: TextView = view.findViewById(R.id.coordinates)
        private val dellTownButton: Button = view.findViewById(R.id.button_dell_town)

        fun bind(town: Town){
            townName.text = town.name
            coordinates.text = "Координаты: ${town.latitude} / ${town.longitude}"
            dellTownButton.setOnClickListener {
                onButtonDeleteClickListener.invoke(adapterPosition, town)
            }
        }
    }
}