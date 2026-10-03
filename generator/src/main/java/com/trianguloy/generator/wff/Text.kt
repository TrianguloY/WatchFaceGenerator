package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Text : Component("Text") {
    var align by delegate(null, Align::class)

    enum class Align { START, CENTER, END, }

    var maxLines by delegate(null as Int?)

    operator fun Font.unaryPlus() = super.add(this)
}