package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt
import java.awt.Color

fun Componentt.Scene(init: Scene.() -> Unit = {}) = Scene(Component("Scene", this)).apply { init() }

class Scene internal constructor(private val component: Component) : Componentt by component {
    var backgroundColor by component.delegate(null as Color?)


    operator fun PartText.unaryPlus() = this@Scene.component.add(this)
    operator fun PartImage.unaryPlus() = this@Scene.component.add(this)
    operator fun PartDraw.unaryPlus() = this@Scene.component.add(this)
    operator fun ComplicationSlot.unaryPlus() = this@Scene.component.add(this)
    operator fun Group.unaryPlus() = this@Scene.component.add(this)
    operator fun DigitalClock.unaryPlus() = this@Scene.component.add(this)
    operator fun Condition.unaryPlus() = this@Scene.component.add(this)
}