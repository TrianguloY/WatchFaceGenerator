package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Compare(expression: String, init: Compare.() -> Unit = {}) = Compare(expression, Component("Compare", this)).apply { init() }

class Compare internal constructor(expression: String, private val component: Component) : Componentt by component {

    var expression by component.delegate(expression)

    operator fun PartText.unaryPlus() = this@Compare.component.add(this)
    operator fun PartImage.unaryPlus() = this@Compare.component.add(this)
    operator fun PartDraw.unaryPlus() = this@Compare.component.add(this)
    operator fun ComplicationSlot.unaryPlus() = this@Compare.component.add(this)
    operator fun Group.unaryPlus() = this@Compare.component.add(this)
    operator fun DigitalClock.unaryPlus() = this@Compare.component.add(this)
    operator fun Condition.unaryPlus() = this@Compare.component.add(this)
}