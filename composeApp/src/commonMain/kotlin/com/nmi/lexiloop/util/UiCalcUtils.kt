package com.nmi.lexiloop.util

//https://github.com/MemoryLeakHub/Top100Albums/blob/main/core/src/main/java/com/vrashkov/core/CommonUtil.kt

fun getFraction(max: Float,  currentOffset: Float) : Float {
    val fraction = 1f - (currentOffset/max)
    return fraction
}
fun calculateCurrentSize(min: Float, max: Float,  fraction: Float): Float {

    if (fraction == 1f) {
        return max
    } else if (fraction == 0f) {
        return min
    }

    val result =  min + ((max - min) * fraction)
    return result
}
