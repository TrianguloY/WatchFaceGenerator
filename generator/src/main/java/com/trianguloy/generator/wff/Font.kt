package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt
import java.awt.Color

fun Componentt.Font(size: Int, init: Font.() -> Unit = {}) = Font(size, Component("Font", this)).apply { init() }
class Font internal constructor(size: Int, private val component: Component) : Componentt by component {
    var family by component.delegate("SYNC_TO_DEVICE")
    var size by component.delegate(size)
    var color by component.delegate(null as Color?)

    operator fun Template.unaryPlus() = this@Font.component.add(this)
}