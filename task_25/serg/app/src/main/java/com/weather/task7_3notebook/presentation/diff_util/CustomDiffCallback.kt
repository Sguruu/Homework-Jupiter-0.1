package com.weather.task7_3notebook.presentation.diff_util

import androidx.recyclerview.widget.DiffUtil

/**
 * Почитать поподробнее можно тут :
 * https://startandroid.ru/ru/blog/504-primer-ispolzovanija-android-diffutil.html
 */
class CustomDiffCallback(
    private val oldListCity: List<*>,
    private val newListCity: List<*>
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
