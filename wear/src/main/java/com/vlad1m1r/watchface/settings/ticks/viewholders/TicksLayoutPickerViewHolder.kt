package com.vlad1m1r.watchface.settings.ticks.viewholders

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.Navigator

class TicksLayoutPickerViewHolder(
    parent: ViewGroup,
    private val dataStorage: DataStorage,
    private val navigator: Navigator
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(
            R.layout.item_settings_ticks_layout_picker,
            parent,
            false
        )
) {

    fun bind(activityResultLauncher: ActivityResultLauncher<Intent>) {
        itemView.findViewById<ConstraintLayout>(R.id.settings_watch_face_picker).apply {
            setOnClickListener {
                navigator.goToTicksLayoutPickerActivityForResult(
                    activityResultLauncher,
                    itemView.context
                )
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