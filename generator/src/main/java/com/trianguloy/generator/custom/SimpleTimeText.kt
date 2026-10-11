package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.DigitalClock
import com.trianguloy.generator.wff.Font
import com.trianguloy.generator.wff.TimeText
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.SimpleTimeText(digitalClockInit: DigitalClock.() -> Unit = {}, text: TimeText.Format) = DigitalClock {
    this.digitalClockInit()
    +TimeText(text) {
        +Font(height)
    }
}