package com.vlad1m1r.watchface.settings.ticks.viewholders

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.FACE_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.settings.tickslayoutpicker.TicksLayoutPickerActivity

class TicksLayoutPickerViewHolder(itemView: View, val dataStorage: DataStorage): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<ConstraintLayout>(R.id.settings_watch_face_picker).apply {
            val intent = Intent(itemView.context, TicksLayoutPickerActivity::class.java)
            setOnClickListener { (itemView.context as Activity).startActivityForResult(intent, FACE_PICKER_REQUEST_CODE) }
        }
    }

    private val image = itemView.findViewById<ImageView>(R.id.image_watch_face).apply {
        this.setImageResource(dataStorage.getTicksLayoutType().drawableZoomedRes)
    }

    fun refreshImage() {
        image.setImageResource(dataStorage.getTicksLayoutType().drawableZoomedRes)
    }
}