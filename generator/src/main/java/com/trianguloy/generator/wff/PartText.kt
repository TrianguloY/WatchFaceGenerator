package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

fun Size.PartText() = PartText(this)
class PartText internal constructor(parent: Size) : Area("PartText", parent) {

    operator fun Text.unaryPlus() = super.add(this)
}
