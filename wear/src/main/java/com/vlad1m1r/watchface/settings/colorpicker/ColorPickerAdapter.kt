package com.vlad1m1r.watchface.settings.colorpicker

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.CustomColorStorage
import com.vlad1m1r.watchface.data.WatchFaceColor
import com.vlad1m1r.watchface.data.toWatchFaceColors
import com.vlad1m1r.watchface.settings.Navigator
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsButtonViewHolder

private const val TYPE_COLORS = 0
private const val TYPE_ADD_COLOR = 1

class ColorPickerAdapter(
    private val customColorStorage: CustomColorStorage,
    private val onColorAction: OnColorAction,
    private val showNoColor: Boolean,
    private val preselectedColor: Int,
    private val navigator: Navigator,
    private val activityResultLauncher: ActivityResultLauncher<Intent>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfColors = emptyList<WatchFaceColor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_COLORS -> ColorsPickerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_color_picker,
                        parent,
                        false
                    ),
                onColorAction,
                preselectedColor
            )
            TYPE_ADD_COLOR -> SettingsButtonViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_button_add,
                        parent,
                        false
                    )
            )
            else -> {
                throw IllegalArgumentException("viewType: $viewType is not supported")
            }
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_COLORS -> {
                (viewHolder as ColorsPickerViewHolder).bind(listOfColors[position].color)
            }
            TYPE_ADD_COLOR -> {
                (viewHolder as SettingsButtonViewHolder).bind(
                    0
                ) {
                    navigator.goToAddCustomColor(
                        activityResultLauncher,
                        viewHolder.itemView.context
                    )
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listOfColors.size + 1
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            in listOfColors.indices -> TYPE_COLORS
            listOfColors.size -> TYPE_ADD_COLOR
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    fun refreshData() {
        listOfColors = customColorStorage.getCustomColors().toWatchFaceColors().filter {
            if(showNoColor) {
                true
            } else {
                it.color != Color.parseColor("#00000000")
            }
        }
        notifyDataSetChanged()
    }


}