package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import java.awt.Color

class Scene : Component("Scene") {
    var backgroundColor by delegate(null as Color?)


    operator fun PartText.unaryPlus() = super.add(this)
}