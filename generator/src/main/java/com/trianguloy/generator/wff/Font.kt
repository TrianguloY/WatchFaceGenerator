package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import java.awt.Color

class Font(size: Int) : Component("Font") {
    var family by delegate("SYNC_TO_DEVICE")
    var size by delegate(size)
    var color by delegate(null as Color?)

    operator fun Template.unaryPlus() = super.add(this)
}