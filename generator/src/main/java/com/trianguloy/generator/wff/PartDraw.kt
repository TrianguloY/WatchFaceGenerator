package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

fun Size.PartDraw() = PartDraw(this)
class PartDraw internal constructor(parent: Size) : Area("PartDraw", parent) {
    var alpha: Int? by delegate(null as Int?)

    operator fun RoundRectangle.unaryPlus() = super.add(this)
}