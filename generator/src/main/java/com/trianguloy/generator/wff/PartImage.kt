package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.PartImage(init: PartImage.() -> Unit = {}) = PartImage(Component("PartImage", this)).apply { init() }

class PartImage internal constructor(private val component: Component) : Componentt by component, Area by AreaImpl(component) {

    operator fun Image.unaryPlus() = this@PartImage.component.add(this)
}