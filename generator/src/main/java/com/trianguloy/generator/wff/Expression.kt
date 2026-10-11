package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt
import com.trianguloy.generator.xml.Cdata

fun Componentt.Expression(name: String, init: Expression.() -> Unit = {}) = Expression(name, Component("Expression", this)).apply { init() }
class Expression internal constructor(name: String, private val component: Component) : Componentt by component {
    var name by component.delegate(name)

    operator fun Cdata.unaryPlus() = this@Expression.component.add(this)
}