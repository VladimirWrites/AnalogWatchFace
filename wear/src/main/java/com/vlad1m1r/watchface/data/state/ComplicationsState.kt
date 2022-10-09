package com.vlad1m1r.watchface.data.state


data class ComplicationsState(
    val hasInAmbientMode: Boolean,
    val hasBiggerTopAndBottomComplications: Boolean,
    val textColor: Int,
    val titleColor: Int,
    val iconColor: Int,
    val borderColor: Int,
    val rangedValuePrimaryColor: Int,
    val rangedValueSecondaryColor: Int,
    val backgroundColor: Int,
)