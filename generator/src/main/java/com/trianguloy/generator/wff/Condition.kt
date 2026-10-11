package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Condition(init: Condition.() -> Unit = {}) = Condition(Component("Condition", this)).apply { init() }
class Condition internal constructor(private val component: Component) : Componentt by component {

    operator fun Expressions.unaryPlus() = this@Condition.component.add(this)
    operator fun Compare.unaryPlus() = this@Condition.component.add(this)
    operator fun Default.unaryPlus() = this@Condition.component.add(this)
}