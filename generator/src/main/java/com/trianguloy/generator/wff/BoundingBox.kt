package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.BoundingBox(init: BoundingBox.() -> Unit = {}) = BoundingBox(Component("BoundingBox", this)).apply { init() }
class BoundingBox internal constructor(private val component: Component) : Componentt by component, Area by AreaImpl(component)