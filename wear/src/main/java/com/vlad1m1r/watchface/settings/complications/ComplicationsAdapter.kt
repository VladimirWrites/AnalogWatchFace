package com.vlad1m1r.watchface.settings.complications

import android.support.wearable.complications.ComplicationProviderInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.complications.viewholder.ComplicationsPickerViewHolder
import com.vlad1m1r.watchface.settings.config.*
import com.vlad1m1r.watchface.settings.config.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.config.viewholders.SettingsWithSwitchViewHolder
import kotlin.IllegalArgumentException

const val TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG = 0
const val TYPE_COMPLICATIONS_AMBIENT_MODE = 1
const val TYPE_COMPLICATIONS_TEXT_COLOR = 2
const val TYPE_COMPLICATIONS_TITLE_COLOR = 3
const val TYPE_COMPLICATIONS_ICON_COLOR = 4
const val TYPE_COMPLICATIONS_BORDER_COLOR = 5
const val TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR = 6
const val TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR = 7
const val TYPE_COMPLICATIONS_BACKGROUND_COLOR = 8
const val TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS = 9
const val TYPE_BIGGER_TEXT = 10

class ComplicationsAdapter(
    private val dataStorage: DataStorage,
    private val colorStorage: ColorStorage,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var complicationsPickerViewHolder: ComplicationsPickerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG -> {
                complicationsPickerViewHolder = ComplicationsPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_complications,
                            parent,
                            false
                        )
                )
                complicationsPickerViewHolder
            }
            TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS,
            TYPE_COMPLICATIONS_AMBIENT_MODE,
            TYPE_BIGGER_TEXT->
                SettingsWithSwitchViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
                            parent,
                            false
                        )
                )
            TYPE_COMPLICATIONS_TEXT_COLOR,
            TYPE_COMPLICATIONS_TITLE_COLOR,
            TYPE_COMPLICATIONS_ICON_COLOR,
            TYPE_COMPLICATIONS_BORDER_COLOR,
            TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR,
            TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR,
            TYPE_COMPLICATIONS_BACKGROUND_COLOR ->
                ColorPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_text,
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
        return 11
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG
            1 -> TYPE_COMPLICATIONS_AMBIENT_MODE
            2 -> TYPE_COMPLICATIONS_TEXT_COLOR
            3 -> TYPE_COMPLICATIONS_TITLE_COLOR
            4 -> TYPE_COMPLICATIONS_ICON_COLOR
            5 -> TYPE_COMPLICATIONS_BORDER_COLOR
            6 -> TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR
            7 -> TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR
            8 -> TYPE_COMPLICATIONS_BACKGROUND_COLOR
            9 -> TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS
            10 -> TYPE_BIGGER_TEXT
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_COMPLICATIONS_AMBIENT_MODE ->
                (viewHolder as SettingsWithSwitchViewHolder).bind(
                    R.string.complications_in_ambient_mode,
                    dataStorage.hasComplicationsInAmbientMode()
                ) {
                    dataStorage.setHasComplicationsInAmbientMode(it)
                }
            TYPE_COMPLICATIONS_TEXT_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.complications_text_color,
                    COMPLICATIONS_TEXT_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsTextColor(),
                    true
                )
            TYPE_COMPLICATIONS_TITLE_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.complications_title_color,
                    COMPLICATIONS_TITLE_COLOR_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsTitleColor(),
                    true
                )
            TYPE_COMPLICATIONS_ICON_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.complications_icon_color,
                    COMPLICATIONS_ICON_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsIconColor(),
                    true
                )
            TYPE_COMPLICATIONS_BORDER_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.complications_border_color,
                    COMPLICATIONS_BORDER_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsBorderColor(),
                    true
                )
            TYPE_COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.complications_ranged_value_primary_color,
                    COMPLICATIONS_RANGED_VALUE_PRIMARY_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsRangedValuePrimaryColor(),
                    true
                )
            TYPE_COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.complications_ranged_value_secondary_color,
                    COMPLICATIONS_RANGED_VALUE_SECONDARY_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsRangedValueSecondaryColor(),
                    true
                )
            TYPE_COMPLICATIONS_BACKGROUND_COLOR ->
                (viewHolder as ColorPickerViewHolder).setData(
                    R.string.complications_background_color,
                    COMPLICATIONS_BACKGROUND_COLOR_PICKER_REQUEST_CODE,
                    colorStorage.getComplicationsBackgroundColor(),
                    true
                )
            TYPE_BIGGER_TOP_AND_BOTTOM_COMPLICATIONS -> {
                (viewHolder as SettingsWithSwitchViewHolder).apply {
                    bind(
                        R.string.bigger_top_and_bottom_complications,
                        dataStorage.hasBiggerTopAndBottomComplications(),
                    ) {
                        dataStorage.setHasBiggerTopAndBottomComplications(it)
                    }
                }
            }
            TYPE_BIGGER_TEXT -> {
                (viewHolder as SettingsWithSwitchViewHolder).apply {
                    bind(
                        R.string.bigger_text_in_complications,
                        dataStorage.hasBiggerComplicationText(),
                    ) {
                        dataStorage.setHasBiggerComplicationText(it)
                    }
                    itemView.setPadding(
                        itemView.paddingLeft,
                        itemView.paddingTop,
                        itemView.paddingRight,
                        itemView.resources.getDimensionPixelSize(R.dimen.bottom_margin)
                    )
                }
            }
        }
    }

    fun updateSelectedComplication(complicationProviderInfo: ComplicationProviderInfo?) {
        if (::complicationsPickerViewHolder.isInitialized) {
            complicationsPickerViewHolder.updateComplicationViews(complicationProviderInfo)
        }
    }

    fun setComplication(
        complicationProviderInfo: ComplicationProviderInfo?,
        watchFaceComplicationId: Int
    ) {
        if (::complicationsPickerViewHolder.isInitialized) {
            complicationsPickerViewHolder.setComplication(
                complicationProviderInfo,
                watchFaceComplicationId
            )
        }
    }
}