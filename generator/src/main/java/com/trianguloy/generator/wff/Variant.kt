package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.Variant.Mode.AMBIENT
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Variant(target: String, value: String, init: Variant.() -> Unit = {}) = Variant(target, value, Component("Variant", this)).apply { init() }

class Variant internal constructor(target: String, value: String, private val component: Component) : Componentt by component {
    val mode by component.delegate(AMBIENT, Mode::class)
    val target by component.delegate(target)
    val value by component.delegate(value)

    enum class Mode {
        AMBIENT
    }
}