package com.vlad1m1r.watchface.hands.viewholder

import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.data.DataProvider
import com.vlad1m1r.watchface.hands.HandsAdapter

class AllHandsViewHolder(itemView: View, private val dataProvider: DataProvider, private val handsAdapter: HandsAdapter): RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<Switch>(R.id.settings_switch).apply {
            isChecked = !dataProvider.hasHands()
            setText(R.string.hide_all_hands)
            setOnCheckedChangeListener { _, isChecked ->
                dataProvider.setHasHands(!isChecked)
                handsAdapter.handsUpdated()

            }
        }
    }
}