package com.vlad1m1r.watchface.settings.background

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.BACKGROUND_LEFT_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.BACKGROUND_RIGHT_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.base.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsWithSwitchViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_COLOR_LEFT = 1
private const val TYPE_COLOR_RIGHT = 2
private const val TYPE_BLACK_AMBIENT = 3

class BackgroundAdapter(
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage,
    @StringRes private val title: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_title,
                        parent,
                        false
                    )
            )
            TYPE_COLOR_LEFT,
            TYPE_COLOR_RIGHT -> ColorPickerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_text,
                        parent,
                        false
                    )
            )
            TYPE_BLACK_AMBIENT -> SettingsWithSwitchViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_switch,
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
        return 4
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_COLOR_LEFT
            2 -> TYPE_COLOR_RIGHT
            3 -> TYPE_BLACK_AMBIENT
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_COLOR_LEFT ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_left_background_color,
                    BACKGROUND_LEFT_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getBackgroundLeftColor(),
                    false
                )
            TYPE_COLOR_RIGHT ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.wear_right_background_color,
                    BACKGROUND_RIGHT_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getBackgroundRightColor(),
                    false
                )
            TYPE_BLACK_AMBIENT ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.wear_black_ambient_background,
                    dataStorage.hasBlackAmbientBackground()
                ) {
                    dataStorage.setHasBlackAmbientBackground(it)
                }
        }
    }
}