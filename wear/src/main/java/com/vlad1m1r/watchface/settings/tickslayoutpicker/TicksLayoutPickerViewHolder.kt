package com.vlad1m1r.watchface.settings.tickslayoutpicker

import android.app.Activity
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.TicksLayoutType

class TicksLayoutPickerViewHolder(itemView: View, private val dataStorage: DataStorage): RecyclerView.ViewHolder(itemView) {

    private lateinit var ticksLayoutType: TicksLayoutType

    private val button = itemView.findViewById<ImageButton>(R.id.watch_face_image)

    fun setTicksLayoutTypeType(ticksLayoutType: TicksLayoutType) {
        this.ticksLayoutType = ticksLayoutType
        initializeTicksLayoutPicker(ticksLayoutType)
    }

    private fun initializeTicksLayoutPicker(ticksLayoutType: TicksLayoutType) {
        button.setImageResource(ticksLayoutType.drawableRes)
        button.setOnClickListener {
            dataStorage.setTicksLayoutType(ticksLayoutType)
            (itemView.context as Activity).setResult(Activity.RESULT_OK)
            (itemView.context as Activity).finish()
        }
    }
}