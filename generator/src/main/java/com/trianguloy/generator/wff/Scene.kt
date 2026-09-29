package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import java.awt.Color

class Scene : Component("Scene") {
    var backgroundColor by delegate(null as Color?)


    operator fun PartText.unaryPlus() = super.add(this)
    operator fun PartImage.unaryPlus() = super.add(this)
    operator fun PartDraw.unaryPlus() = super.add(this)
    operator fun ComplicationSlot.unaryPlus() = super.add(this)
}