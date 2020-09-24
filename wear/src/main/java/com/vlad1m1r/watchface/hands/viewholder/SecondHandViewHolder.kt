package com.vlad1m1r.watchface.hands.viewholder

import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider

class SecondHandViewHolder(itemView: View, private val dataProvider: DataProvider): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<Switch>(R.id.settings_switch).apply {
            isEnabled = dataProvider.hasHands()
            isChecked = !dataProvider.hasSecondHand()
            setText(R.string.hide_second_hand)
            setOnCheckedChangeListener { _, isChecked ->
                dataProvider.setHasSecondHand(!isChecked)
            }
        }
    }
}