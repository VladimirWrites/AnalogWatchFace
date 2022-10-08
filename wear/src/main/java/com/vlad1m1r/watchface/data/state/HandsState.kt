package com.vlad1m1r.watchface.data.state

data class HandsState(
    val hasSmoothSecondsHand: Boolean,
    val hasSecondHand: Boolean,
    val secondsHand: HandState,
    val minutesHand: HandState,
    val hoursHand: HandState,
    val circleState: CircleState,
    val hasInInteractive: Boolean,
    val hasCenterCircleInAmbientMode: Boolean,
    val shouldKeepHandColorInAmbientMode: Boolean,
    val hasHands: Boolean,
)