package com.vlad1m1r.watchface.settings.base.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.model.Point
import com.vlad1m1r.watchface.settings.base.WatchPreviewView

class WatchPreviewViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    private val handPreview = itemView.findViewById<WatchPreviewView>(R.id.hand_preview).apply {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    fun bind(center: Point) {
        handPreview.invalidate(center)
    }
}