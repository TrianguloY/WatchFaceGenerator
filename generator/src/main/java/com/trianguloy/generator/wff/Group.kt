package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

private var nextGroupId = 0

fun Size.Group() = Group(this)

class Group internal constructor(parent: Size) : Area("Group", parent) {
    var name by delegate("group_${nextGroupId++}")

    operator fun Condition.unaryPlus() = super.add(this)
}