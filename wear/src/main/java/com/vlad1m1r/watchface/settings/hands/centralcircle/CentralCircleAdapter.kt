package com.vlad1m1r.watchface.settings.hands.centralcircle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.settings.CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.base.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_COLOR_CENTRAL_CIRCLE = 1

class CentralCircleAdapter(
    private val colorStorage: ColorStorage,
    @StringRes private val title: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_TITLE -> viewHolder =
                TitleViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_title,
                            parent,
                            false
                        )
                )
            TYPE_COLOR_CENTRAL_CIRCLE -> viewHolder =
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
            0 -> TYPE_TITLE
            1 -> TYPE_COLOR_CENTRAL_CIRCLE
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_COLOR_CENTRAL_CIRCLE -> (viewHolder as ColorPickerViewHolder).setData(
                R.string.wear_central_circle_color,
                CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE,
                colorStorage.getCentralCircleColor(),
                true
            )
        }
    }
}