package com.weather.task7_3notebook.view.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Класс для установки отступа между элементами RecyclerView
 */
class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // устанавливаем отступ снизу
        outRect.bottom = verticalSpaceHeight
    }
}
