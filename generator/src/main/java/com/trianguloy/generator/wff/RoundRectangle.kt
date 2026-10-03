package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

fun Size.RoundRectangle() = RoundRectangle(this)
class RoundRectangle internal constructor(parent: Size) : Area("RoundRectangle", parent) {
    var cornerRadiusX by delegate(10)
    var cornerRadiusY by delegate(10)

    operator fun Fill.unaryPlus() = super.add(this)
    operator fun Stroke.unaryPlus() = super.add(this)
}