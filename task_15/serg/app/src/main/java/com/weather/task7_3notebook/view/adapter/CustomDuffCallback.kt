package com.weather.task7_3notebook.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.weather.task7_3notebook.model.Contact

/**
 * Почитать поподробнее можно тут :
 * https://startandroid.ru/ru/blog/504-primer-ispolzovanija-android-diffutil.html
 */
class CustomDuffCallback(
    private val oldListCity: List<Contact>,
    private val newListCity: List<Contact>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldListCity.size
    }

    override fun getNewListSize(): Int {
        return newListCity.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemCity = oldListCity[oldItemPosition]
        val newItemCity = newListCity[newItemPosition]
        return oldItemCity == newItemCity
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemCity = oldListCity[oldItemPosition]
        val newItemCity = newListCity[newItemPosition]
        return oldItemCity == newItemCity
    }
}
