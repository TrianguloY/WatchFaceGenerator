package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Expressions(init: Expressions.() -> Unit = {}) = Expressions(Component("Expressions", this)).apply { init() }
class Expressions internal constructor(private val component: Component) : Componentt by component {

    operator fun Expression.unaryPlus() = this@Expressions.component.add(this)
}