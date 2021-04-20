package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.background.GetBackgroundData
import com.vlad1m1r.watchface.components.hands.GetHandData
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.SizeStorage
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.base.WatchPreviewView

class WatchPreviewViewHolder(
    itemView: View,
    colorStorage: ColorStorage,
    dataStorage: DataStorage,
    sizeStorage: SizeStorage
): RecyclerView.ViewHolder(itemView) {
    private val paintDataProvider = GetHandData(itemView.context, colorStorage, sizeStorage)
    private val getBackgroundData = GetBackgroundData(colorStorage, dataStorage)
    private val handPreview = itemView.findViewById<WatchPreviewView>(R.id.hand_preview).apply {
        initialize(paintDataProvider, getBackgroundData)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    fun bind(center: Point) {
        handPreview.invalidate(center)
    }
}