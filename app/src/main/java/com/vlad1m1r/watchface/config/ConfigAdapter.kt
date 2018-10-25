package com.vlad1m1r.watchface.config

import android.support.v7.widget.RecyclerView
import android.support.wearable.complications.ComplicationProviderInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.utils.DataProvider
import java.lang.IllegalArgumentException

const val TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG = 0
const val TYPE_COMPLICATIONS_AMBIENT_MODE = 1

class ConfigAdapter(private val dataProvider: DataProvider) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var complicationsViewHolder: ComplicationsViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG -> {
                complicationsViewHolder = ComplicationsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_complications,
                            parent,
                            false
                        )
                )
                viewHolder = complicationsViewHolder
            }

            TYPE_COMPLICATIONS_AMBIENT_MODE -> viewHolder = ComplicationsAmbientViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_ambient_complications,
                        parent,
                        false
                    ),
                dataProvider
            )
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_PREVIEW_AND_COMPLICATIONS_CONFIG
            1 -> TYPE_COMPLICATIONS_AMBIENT_MODE
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

    }

    fun updateSelectedComplication(complicationProviderInfo: ComplicationProviderInfo?) {
        complicationsViewHolder.updateComplicationViews(complicationProviderInfo)
    }

    fun setComplication(complicationProviderInfo: ComplicationProviderInfo?, watchFaceComplicationId: Int) {
        complicationsViewHolder.setComplication(complicationProviderInfo, watchFaceComplicationId)
    }
}