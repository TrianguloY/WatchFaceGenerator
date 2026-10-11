package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.DigitalClock(init: DigitalClock.() -> Unit = {}) = DigitalClock(Component("DigitalClock", this)).apply { init() }

class DigitalClock internal constructor(private val component: Component) : Componentt by component, Area by AreaImpl(component) {

    operator fun TimeText.unaryPlus() = this@DigitalClock.component.add(this)
}