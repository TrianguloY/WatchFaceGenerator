package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size
import kotlin.math.roundToInt


// Move to a helper
var Area.horizontal: Pair<Int, Int>
    get() = x to (x + width)
    set(value) {
        x = value.first
        width = value.second - value.first
    }
var Area.vertical: Pair<Int, Int>
    get() = y to (y + height)
    set(value) {
        y = value.first
        height = value.second - value.first
    }

fun Size.ph(h: Double) = (h * parentWidth).roundToInt()
fun Size.pv(v: Double) = (v * parentHeight).roundToInt()