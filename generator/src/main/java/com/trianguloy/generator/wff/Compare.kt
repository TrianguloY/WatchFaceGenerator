package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Compare(expression: String) : Component("Compare") {
    var expression by delegate(expression)

    operator fun PartText.unaryPlus() = super.add(this)
    operator fun PartImage.unaryPlus() = super.add(this)
    operator fun PartDraw.unaryPlus() = super.add(this)
    operator fun ComplicationSlot.unaryPlus() = super.add(this)
    operator fun Group.unaryPlus() = super.add(this)
    operator fun DigitalClock.unaryPlus() = super.add(this)
    operator fun Condition.unaryPlus() = super.add(this)
}