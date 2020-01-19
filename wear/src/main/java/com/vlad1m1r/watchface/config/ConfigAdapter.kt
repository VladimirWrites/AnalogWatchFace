package com.vlad1m1r.watchface.config

import android.support.wearable.complications.ComplicationProviderInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.config.viewholders.*
import com.vlad1m1r.watchface.data.DataProvider
import java.lang.IllegalArgumentException

const val TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG = 0
const val TYPE_LAYOUT2 = 1
const val TYPE_COMPLICATIONS_AMBIENT_MODE = 2
const val TYPE_TICKS_AMBIENT_MODE = 3
const val TYPE_TICKS_INTERACTIVE_MODE = 4
const val TYPE_BACKGROUND_BLACK = 5
const val TYPE_RATE = 6
const val TYPE_SECOND_HAND = 7

class ConfigAdapter(private val dataProvider: DataProvider) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var complicationsPickerViewHolder: ComplicationsPickerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG -> {
                complicationsPickerViewHolder = ComplicationsPickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_complications,
                            parent,
                            false
                        )
                )
                viewHolder = complicationsPickerViewHolder
            }

            TYPE_LAYOUT2 -> viewHolder =
                Layout2ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
                            parent,
                            false
                        ),
                    dataProvider
                )

            TYPE_COMPLICATIONS_AMBIENT_MODE -> viewHolder =
                    ComplicationsAmbientViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(
                                R.layout.item_settings_switch,
                                parent,
                                false
                            ),
                        dataProvider
                    )

            TYPE_TICKS_AMBIENT_MODE -> viewHolder = TicksAmbientViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_switch,
                        parent,
                        false
                    ),
                dataProvider
            )

            TYPE_TICKS_INTERACTIVE_MODE -> viewHolder =
                    TicksInteractiveViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(
                                R.layout.item_settings_switch,
                                parent,
                                false
                            ),
                        dataProvider
                    )

            TYPE_BACKGROUND_BLACK -> viewHolder =
                BlackBackgroundViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
                            parent,
                            false
                        ),
                    dataProvider
                )

            TYPE_RATE -> viewHolder =
                    RateViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(
                                R.layout.item_settings_rate,
                                parent,
                                false
                            ),
                        RateApp(parent.context)
                    )
            TYPE_SECOND_HAND -> viewHolder =
                SecondHandViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
                            parent,
                            false
                        ),
                    dataProvider
                )
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG
            1 -> TYPE_LAYOUT2
            2 -> TYPE_COMPLICATIONS_AMBIENT_MODE
            3 -> TYPE_TICKS_AMBIENT_MODE
            4 -> TYPE_TICKS_INTERACTIVE_MODE
            5 -> TYPE_BACKGROUND_BLACK
            6 -> TYPE_RATE
            7 -> TYPE_SECOND_HAND
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

    }

    fun updateSelectedComplication(complicationProviderInfo: ComplicationProviderInfo?) {
        if(::complicationsPickerViewHolder.isInitialized) {
            complicationsPickerViewHolder.updateComplicationViews(complicationProviderInfo)
        }
    }

    fun setComplication(complicationProviderInfo: ComplicationProviderInfo?, watchFaceComplicationId: Int) {
        if(::complicationsPickerViewHolder.isInitialized) {
            complicationsPickerViewHolder.setComplication(
                complicationProviderInfo,
                watchFaceComplicationId
            )
        }
    }
}