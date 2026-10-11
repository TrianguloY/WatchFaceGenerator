package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Default(init: Default.() -> Unit = {}) = Default(Component("Default", this)).apply { init() }
class Default internal constructor(private val component: Component) : Componentt by component {
    operator fun PartText.unaryPlus() = this@Default.component.add(this)
    operator fun PartImage.unaryPlus() = this@Default.component.add(this)
    operator fun PartDraw.unaryPlus() = this@Default.component.add(this)
    operator fun ComplicationSlot.unaryPlus() = this@Default.component.add(this)
    operator fun Group.unaryPlus() = this@Default.component.add(this)
    operator fun DigitalClock.unaryPlus() = this@Default.component.add(this)
    operator fun Condition.unaryPlus() = this@Default.component.add(this)
}