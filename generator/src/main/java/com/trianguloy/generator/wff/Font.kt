package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Font(size: Int) : Component("Font") {
    var family by delegate("SYNC_TO_DEVICE")
    var size by delegate(size)

    operator fun Template.unaryPlus() = super.add(this)
}