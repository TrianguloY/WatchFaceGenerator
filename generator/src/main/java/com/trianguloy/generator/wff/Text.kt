package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Text(init: Text.() -> Unit = {}) = Text(Component("Text", this)).apply { init() }

class Text internal constructor(private val component: Component) : Componentt by component {
    var align by component.delegate(null, Align::class)

    enum class Align { START, CENTER, END, }

    var maxLines by component.delegate(null as Int?)

    operator fun Font.unaryPlus() = this@Text.component.add(this)
}