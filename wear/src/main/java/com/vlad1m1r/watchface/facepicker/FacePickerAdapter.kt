package com.vlad1m1r.watchface.facepicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.data.WatchFaceType.ORIGINAL
import com.vlad1m1r.watchface.data.WatchFaceType.WATCH_FACE_1
import java.lang.IllegalArgumentException

const val TYPE_FACE = 0

class FacePickerAdapter(private val dataProvider: DataProvider) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_FACE -> {
                return FacePickerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_face_picker,
                            parent,
                            false
                        ),
                    dataProvider
                )
            }
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0,1 -> TYPE_FACE
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val facePickerViewHolder = (viewHolder as FacePickerViewHolder)
        when (position) {
            0 -> {
                facePickerViewHolder.setWatchFaceType(ORIGINAL)
            }
            1 -> {
                facePickerViewHolder.setWatchFaceType(WATCH_FACE_1)
            }
        }

    }
}