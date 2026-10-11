package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.TimeText.Format
import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.TimeText(format: Format, init: TimeText.() -> Unit = {}) = TimeText(format, Component("TimeText", this)).apply { init() }


class TimeText internal constructor(format: Format, private val component: Component) : Componentt by component, Area by AreaImpl(component) {
    enum class Format(internal val value: String) {
        HOUR("hh"),
        MINUTES("mm"),
    }

    var format: Format by component.delegate(format, { v -> Format.entries.firstOrNull { it.value == v } ?: format }, { it.value })


    operator fun Font.unaryPlus() = this@TimeText.component.add(this)
}