package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt
import java.awt.Color.WHITE

fun Componentt.Stroke(init: Stroke.() -> Unit = {}) = Stroke(Component("Stroke", this)).apply { init() }

class Stroke internal constructor(private val component: Component) : Componentt by component {
    var color by component.delegate(WHITE)
    var thickness by component.delegate(1)
    var dashIntervals by component.delegate(null as List<Double>?)
}