package com.vlad1m1r.watchface.hands

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.hands.viewholder.AllHandsViewHolder
import com.vlad1m1r.watchface.hands.viewholder.SecondHandViewHolder
import java.lang.IllegalArgumentException

const val TYPE_ALL_HANDS = 0
const val TYPE_SECOND_HAND = 1

class HandsAdapter(private val dataProvider: DataProvider) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            TYPE_ALL_HANDS -> viewHolder =
                AllHandsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
                            parent,
                            false
                        ),
                    dataProvider,
                    this
                )
            TYPE_SECOND_HAND -> viewHolder =
                SecondHandViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_settings_switch,
                            parent,
                            false
                        ),
                    dataProvider
                )
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> TYPE_ALL_HANDS
            1 -> TYPE_SECOND_HAND
            else -> throw IllegalArgumentException("Unsupported View Type position: $position")
        }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

    }

    fun handsUpdated() {
        this.notifyItemChanged(1)
    }
}