package com.vlad1m1r.watchface.settings.hands.hand

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.components.hands.PaintDataProvider
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.model.Point

class WatchPreviewViewHolder(
    itemView: View,
    colorStorage: ColorStorage
): RecyclerView.ViewHolder(itemView) {
    private val paintDataProvider = PaintDataProvider(itemView.context, colorStorage)
    private val handPreview = itemView.findViewById<WatchPreviewView>(R.id.hand_preview).apply {
        initialize(paintDataProvider)
    }

    fun bind(center: Point) {
        handPreview.invalidate(center)
    }
}