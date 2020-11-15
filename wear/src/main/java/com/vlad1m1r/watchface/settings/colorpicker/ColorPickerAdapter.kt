package com.vlad1m1r.watchface.settings.colorpicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R

class ColorPickerAdapter(
    private val colorProvider: ColorProvider,
    private val onColorSelected: OnColorSelected,
    private val showNoColor: Boolean,
    private val preselectedColor: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ColorsPickerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_color_picker,
                    parent,
                    false
                ),
            onColorSelected,
            preselectedColor
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val (firstColor, secondColor, thirdColor) = colorProvider(position, showNoColor)
        (holder as ColorsPickerViewHolder).setColors(firstColor, secondColor, thirdColor)
    }

    override fun getItemCount(): Int {
        return 12
    }
}