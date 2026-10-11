package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.PartDraw(init: PartDraw.() -> Unit = {}) = PartDraw(Component("PartDraw", this)).apply { init() }

class PartDraw internal constructor(private val component: Component) : Componentt by component, Area by AreaImpl(component) {
    var alpha: Int? by component.delegate(null as Int?)

    operator fun RoundRectangle.unaryPlus() = this@PartDraw.component.add(this)
}