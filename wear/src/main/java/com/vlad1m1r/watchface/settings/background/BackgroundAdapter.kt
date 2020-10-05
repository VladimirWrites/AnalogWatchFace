package com.vlad1m1r.watchface.settings.background

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.settings.config.BACKGROUND_LEFT_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.config.BACKGROUND_RIGHT_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.config.viewholders.ColorPickerViewHolder
import java.lang.IllegalArgumentException

const val TYPE_COLOR_LEFT = 0
const val TYPE_COLOR_RIGHT = 1

class BackgroundAdapter(private val dataStorage: DataStorage, private val colorStorage: ColorStorage) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_COLOR_LEFT,
            TYPE_COLOR_RIGHT -> viewHolder =
                ColorPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_text,
                            parent,
                            false
                        )
                )
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_COLOR_LEFT
            1 -> TYPE_COLOR_RIGHT
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder.itemViewType) {
            TYPE_COLOR_LEFT ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.left_background_color,
                    BACKGROUND_LEFT_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getBackgroundLeftColor(),
                    false
                )
            TYPE_COLOR_RIGHT ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.right_background_color,
                    BACKGROUND_RIGHT_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getBackgroundRightColor(),
                    false
                )
        }
    }
}