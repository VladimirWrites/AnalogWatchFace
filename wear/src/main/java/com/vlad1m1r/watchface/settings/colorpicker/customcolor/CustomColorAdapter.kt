package com.vlad1m1r.watchface.settings.colorpicker.customcolor

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.base.viewholders.*

private const val TYPE_TITLE = 0
private const val TYPE_COLOR_PREVIEW = 1
private const val TYPE_RED_COLOR_SCALE = 2
private const val TYPE_GREEN_COLOR_SCALE = 3
private const val TYPE_BLUE_COLOR_SCALE = 4
private const val TYPE_CONFIRM = 5

class CustomColorAdapter(
    @StringRes private val title: Int,
    private val onButtonConfirmed: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var colorRed: Int = 128
    private var colorGreen: Int = 128
    private var colorBlue: Int = 128

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(parent)
            TYPE_COLOR_PREVIEW -> ColorsPreviewViewHolder(parent)
            TYPE_RED_COLOR_SCALE,
            TYPE_GREEN_COLOR_SCALE,
            TYPE_BLUE_COLOR_SCALE -> SettingsSliderViewHolder(parent)
            TYPE_CONFIRM -> SettingsButtonViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_button,
                        parent,
                        false
                    )
            )
            else -> {
                throw IllegalArgumentException("viewType: $viewType is not supported")
            }
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_COLOR_PREVIEW
            2 -> TYPE_RED_COLOR_SCALE
            3 -> TYPE_GREEN_COLOR_SCALE
            4 -> TYPE_BLUE_COLOR_SCALE
            5 -> TYPE_CONFIRM
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE -> (viewHolder as TitleViewHolder).bind(
                title
            )
            TYPE_COLOR_PREVIEW -> {
                (viewHolder as ColorsPreviewViewHolder).bind(
                    Color.rgb(colorRed, colorGreen, colorBlue)
                )
            }
            TYPE_RED_COLOR_SCALE -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_custom_color_red,
                colorRed,
                0,
                255,
            ) { red ->
                this.colorRed = red
                notifyDataSetChanged()
            }

            TYPE_GREEN_COLOR_SCALE -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_custom_color_green,
                colorGreen,
                0,
                255,
            ) { green ->
                this.colorGreen = green
                notifyDataSetChanged()
            }

            TYPE_BLUE_COLOR_SCALE -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_custom_color_blue,
                colorBlue,
                0,
                255,
            ) { blue ->
                this.colorBlue = blue
                notifyDataSetChanged()
            }

            TYPE_CONFIRM ->
                (viewHolder as SettingsButtonViewHolder).bind(
                    R.string.wear_custom_color_confirm,
                ) {
                    onButtonConfirmed(Color.rgb(colorRed, colorGreen, colorBlue))
                }
        }
    }
}