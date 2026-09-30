package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

fun Size.DigitalClock() = DigitalClock(this)

class DigitalClock internal constructor(parent: Size) : Area("DigitalClock", parent) {

    operator fun TimeText.unaryPlus() = super.add(this)
}