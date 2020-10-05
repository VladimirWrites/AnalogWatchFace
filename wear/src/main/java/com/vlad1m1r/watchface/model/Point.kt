package com.vlad1m1r.watchface.model

class Point(val x: Float = 0f, val y: Float = 0f) {
    operator fun component1(): Float = x
    operator fun component2(): Float = y
}