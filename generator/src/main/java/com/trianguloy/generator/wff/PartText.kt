package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.PartText(init: PartText.() -> Unit = {}) = PartText(Component("PartText", this)).apply { init() }
class PartText internal constructor(private val component: Component) : Componentt by component, Area by AreaImpl(component) {

    operator fun Text.unaryPlus() = this@PartText.component.add(this)
}
