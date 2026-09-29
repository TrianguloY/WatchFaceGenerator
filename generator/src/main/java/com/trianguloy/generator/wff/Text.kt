package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Text : Component("Text") {
    val align by delegate(null, Align::class)

    enum class Align {
        START,
        CENTER,
        END,
    }

    operator fun Font.unaryPlus() = super.add(this)
}