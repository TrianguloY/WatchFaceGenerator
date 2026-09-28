package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Complication(type: ComplicationType) : Component("Complication") {
    var type by delegate(type, ComplicationType::class.java)

    operator fun PartImage.unaryPlus() = super.add(this)
    operator fun PartText.unaryPlus() = super.add(this)
}