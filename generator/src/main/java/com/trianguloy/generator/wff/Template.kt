package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt
import com.trianguloy.generator.xml.Cdata

fun Componentt.Template(init: Template.() -> Unit = {}) = Template(Component("Template", this)).apply { init() }

class Template internal constructor(private val component: Component) : Componentt by component {
    operator fun Cdata.unaryPlus() = component.add(this)

    operator fun Parameter.unaryPlus() = this@Template.component.add(this)
}