package com.vlad1m1r.watchface.settings.tickslayoutpicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.TicksLayoutType.*
import kotlin.IllegalArgumentException

const val TYPE_TICKS_LAYOUT = 0

class TickLayoutPickerAdapter(private val dataStorage: DataStorage) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TICKS_LAYOUT -> {
                TicksLayoutPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_ticks_picker,
                            parent,
                            false
                        ),
                    dataStorage
                )
            }
            else -> {
                throw IllegalArgumentException("viewType: $viewType is not supported")
            }
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0, 1, 2, 3 -> TYPE_TICKS_LAYOUT
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val ticksLayoutPickerViewHolder = (viewHolder as TicksLayoutPickerViewHolder)
        when (position) {
            0 -> {
                ticksLayoutPickerViewHolder.setTicksLayoutTypeType(ORIGINAL)
            }
            1 -> {
                ticksLayoutPickerViewHolder.setTicksLayoutTypeType(TICKS_LAYOUT_1)
            }
            2 -> {
                ticksLayoutPickerViewHolder.setTicksLayoutTypeType(TICKS_LAYOUT_2)
            }
            3 -> {
                ticksLayoutPickerViewHolder.setTicksLayoutTypeType(TICKS_LAYOUT_3)
            }
        }

    }
}