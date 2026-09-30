package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.TimeText.Format
import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

fun Size.TimeText(format: Format) = TimeText(format, this)

class TimeText internal constructor(format: Format, parent: Size) : Area("TimeText", parent) {
    enum class Format(internal val value: String) {
        HOUR("hh"),
        MINUTES("mm"),
    }

    var format: Format by delegate(format, { v -> Format.entries.firstOrNull { it.value == v } ?: format }, { it.value })


    operator fun Font.unaryPlus() = super.add(this)
}