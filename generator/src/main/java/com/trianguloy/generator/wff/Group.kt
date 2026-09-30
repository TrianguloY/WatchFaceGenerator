package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

private var nextGroupId = 0

fun Size.Group() = Group(this)

class Group internal constructor(parent: Size) : Area("Group", parent) {
    var name by delegate("group_${nextGroupId++}")

    operator fun PartText.unaryPlus() = super.add(this)
    operator fun PartImage.unaryPlus() = super.add(this)
    operator fun PartDraw.unaryPlus() = super.add(this)
    operator fun ComplicationSlot.unaryPlus() = super.add(this)
    operator fun Group.unaryPlus() = super.add(this)
    operator fun DigitalClock.unaryPlus() = super.add(this)
    operator fun Condition.unaryPlus() = super.add(this)
}