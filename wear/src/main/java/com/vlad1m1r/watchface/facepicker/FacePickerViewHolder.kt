package com.vlad1m1r.watchface.facepicker

import android.app.Activity
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.config.FACE_PICKER_REQUEST_CODE
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.data.WatchFaceType

class FacePickerViewHolder(itemView: View, private val dataProvider: DataProvider): RecyclerView.ViewHolder(itemView) {

    private lateinit var watchFaceType: WatchFaceType

    private val button = itemView.findViewById<ImageButton>(R.id.watch_face_image)

    fun setWatchFaceType(watchFaceType: WatchFaceType) {
        this.watchFaceType = watchFaceType
        initializeFacePicker(watchFaceType)
    }

    private fun initializeFacePicker(watchFaceType: WatchFaceType) {
        button.setImageResource(watchFaceType.drawableRes)
        button.setOnClickListener {
            dataProvider.setWatchFaceType(watchFaceType)
            (itemView.context as Activity).setResult(Activity.RESULT_OK)
            (itemView.context as Activity).finish()
        }
    }
}