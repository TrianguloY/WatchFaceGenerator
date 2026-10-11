package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

private var nextGroupId = 0

fun Componentt.Group(init: Group.() -> Unit = {}) = Group(Component("Group", this)).apply { init() }

class Group internal constructor(private val component: Component) : Componentt by component, Area by AreaImpl(component) {
    var name by component.delegate("group_${nextGroupId++}")

    operator fun PartText.unaryPlus() = this@Group.component.add(this)
    operator fun PartImage.unaryPlus() = this@Group.component.add(this)
    operator fun PartDraw.unaryPlus() = this@Group.component.add(this)
    operator fun Group.unaryPlus() = this@Group.component.add(this)
    operator fun DigitalClock.unaryPlus() = this@Group.component.add(this)
    operator fun Condition.unaryPlus() = this@Group.component.add(this)
}