package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.Variant.Mode.AMBIENT
import com.trianguloy.generator.wff.helpers.Component

class Variant(target: String, value: String) : Component("Variant") {
    val mode by delegate(AMBIENT, Mode::class)
    val target by delegate(target)
    val value by delegate(value)

    enum class Mode {
        AMBIENT
    }
}