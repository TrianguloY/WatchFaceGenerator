package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Parameter(expression: String, init: Parameter.() -> Unit = {}) = Parameter(expression, Component("Parameter", this)).apply { init() }
class Parameter internal constructor(expression: String, private val component: Component) : Componentt by component {
    var expression by component.delegate(expression)
}