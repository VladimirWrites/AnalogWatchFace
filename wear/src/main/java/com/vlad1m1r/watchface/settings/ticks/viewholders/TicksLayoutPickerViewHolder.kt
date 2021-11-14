package com.vlad1m1r.watchface.settings.ticks.viewholders

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.FACE_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.Navigator

class TicksLayoutPickerViewHolder(
    itemView: View,
    private val dataStorage: DataStorage,
    private val navigator: Navigator
    ): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<ConstraintLayout>(R.id.settings_watch_face_picker).apply {
            setOnClickListener {
                navigator.goToTicksLayoutPickerActivityForResult((itemView.context as Activity), FACE_PICKER_REQUEST_CODE)
            }
        }
    }

    private val image = itemView.findViewById<ImageView>(R.id.image_watch_face).apply {
        this.setImageResource(dataStorage.getTicksLayoutType().drawableZoomedRes)
    }

    fun refreshImage() {
        image.setImageResource(dataStorage.getTicksLayoutType().drawableZoomedRes)
    }
}