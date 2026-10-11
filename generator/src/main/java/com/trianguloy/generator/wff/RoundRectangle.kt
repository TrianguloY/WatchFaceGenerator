package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.RoundRectangle(init: RoundRectangle.() -> Unit = {}) = RoundRectangle(Component("RoundRectangle", this)).apply { init() }
class RoundRectangle internal constructor(private val component: Component) : Componentt by component, Area by AreaImpl(component) {
    var cornerRadiusX by component.delegate(10)
    var cornerRadiusY by component.delegate(10)

    operator fun Fill.unaryPlus() = this@RoundRectangle.component.add(this)
    operator fun Stroke.unaryPlus() = this@RoundRectangle.component.add(this)
}