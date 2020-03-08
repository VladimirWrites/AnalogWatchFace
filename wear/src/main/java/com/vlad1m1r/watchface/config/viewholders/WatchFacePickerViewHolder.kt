package com.vlad1m1r.watchface.config.viewholders

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.config.FACE_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.facepicker.FacePickerActivity

class WatchFacePickerViewHolder(itemView: View, val dataProvider: DataProvider): RecyclerView.ViewHolder(itemView) {
    private val watchFacePicker = itemView.findViewById<ConstraintLayout>(R.id.settings_watch_face_picker).apply {
        val intent = Intent(itemView.context, FacePickerActivity::class.java)
        setOnClickListener { (itemView.context as Activity).startActivityForResult(intent, FACE_PICKER_REQUEST_CODE) }
    }

    private val image = itemView.findViewById<ImageView>(R.id.image_watch_face).apply {
        this.setImageResource(dataProvider.getWatchFaceType().drawableRes)
    }

    fun refreshImage() {
        image.setImageResource(dataProvider.getWatchFaceType().drawableRes)
    }
}