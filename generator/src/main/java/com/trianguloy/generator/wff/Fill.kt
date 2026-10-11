package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt
import java.awt.Color

fun Componentt.Fill(init: Fill.() -> Unit = {}) = Fill(Component("Fill", this)).apply { init() }

class Fill internal constructor(private val component: Component) : Componentt by component {
    var color by component.delegate(Color.WHITE)
}