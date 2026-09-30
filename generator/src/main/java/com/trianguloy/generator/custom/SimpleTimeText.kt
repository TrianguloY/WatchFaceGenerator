package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.DigitalClock
import com.trianguloy.generator.wff.Font
import com.trianguloy.generator.wff.TimeText
import com.trianguloy.generator.wff.helpers.Size

fun Size.SimpleTimeText(digitalClockInit: DigitalClock.() -> Unit = {}, text: TimeText.Format) = DigitalClock().apply {
    this.digitalClockInit()
    +TimeText(text).apply {
        +Font(height)
    }
}