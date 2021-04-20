package com.vlad1m1r.watchface.settings.hands.centralcircle

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.settings.base.viewholders.ColorPickerViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.SettingsSliderViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.TitleViewHolder
import com.vlad1m1r.watchface.settings.base.viewholders.WatchPreviewViewHolder
import java.lang.IllegalArgumentException

private const val TYPE_TITLE = 0
private const val TYPE_PREVIEW = 1
private const val TYPE_COLOR_CENTRAL_CIRCLE = 2
private const val TYPE_CENTRAL_CIRCLE_WIDTH = 3
private const val TYPE_CENTRAL_CIRCLE_RADIUS = 4

class CentralCircleAdapter(
    private val colorStorage: ColorStorage,
    private val dataStorage: DataStorage,
    private val sizeStorage: SizeStorage,
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
            TYPE_PREVIEW -> WatchPreviewViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_hand_preview,
                        parent,
                        false
                    ),
                colorStorage,
                dataStorage,
                sizeStorage
            )
            TYPE_COLOR_CENTRAL_CIRCLE -> ColorPickerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_text,
                        parent,
                        false
                    )
            )
            TYPE_CENTRAL_CIRCLE_WIDTH -> SettingsSliderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_slider,
                        parent,
                        false
                    )
            )
            TYPE_CENTRAL_CIRCLE_RADIUS -> SettingsSliderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_settings_slider,
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
        return 5
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_TITLE
            1 -> TYPE_PREVIEW
            2 -> TYPE_COLOR_CENTRAL_CIRCLE
            3 -> TYPE_CENTRAL_CIRCLE_WIDTH
            4 -> TYPE_CENTRAL_CIRCLE_RADIUS
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_TITLE ->
                (viewHolder as TitleViewHolder).bind(
                    title
                )
            TYPE_PREVIEW -> {
                val context = viewHolder.itemView.context
                val width = (context as Activity).window.decorView.width.toFloat()
                val height = context.resources.getDimension(R.dimen.item_watch_preview_height)
                (viewHolder as WatchPreviewViewHolder).bind(
                    Point(width / 2, height / 2),
                )
            }
            TYPE_COLOR_CENTRAL_CIRCLE -> (viewHolder as ColorPickerViewHolder).setData(
                R.string.wear_central_circle_color,
                CENTRAL_CIRCLE_COLOR_PICKER_REQUEST_CODE,
                colorStorage.getCentralCircleColor(),
                true
            )
            TYPE_CENTRAL_CIRCLE_WIDTH -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_circle_width,
                sizeStorage.getCircleWidth(),
                1,
                sizeStorage.getCircleRadius()
            ) { circleWidth ->
                sizeStorage.setCircleWidth(circleWidth)
                notifyDataSetChanged()
            }

            TYPE_CENTRAL_CIRCLE_RADIUS -> (viewHolder as SettingsSliderViewHolder).setData(
                R.string.wear_circle_radius,
                sizeStorage.getCircleRadius(),
                1,
                15
            ) { circleRadius ->
                sizeStorage.setCircleRadius(circleRadius)
                notifyDataSetChanged()
            }
        }
    }
}