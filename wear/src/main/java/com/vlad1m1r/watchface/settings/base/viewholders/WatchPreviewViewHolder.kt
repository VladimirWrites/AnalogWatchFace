package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.base.WatchPreviewView

class WatchPreviewViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(
            R.layout.item_settings_hand_preview,
            parent,
            false
        )
) {
    private val handPreview = itemView.findViewById<WatchPreviewView>(R.id.hand_preview).apply {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    fun bind(center: Point) {
        handPreview.invalidate(center)
    }
}