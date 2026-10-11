package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.ComplicationType.EMPTY
import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.AreaImpl
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

private var nextSlotId = 0

fun Componentt.ComplicationSlot(init: ComplicationSlot.() -> Unit = {}) = ComplicationSlot(Component("ComplicationSlot", this)).apply { init() }
class ComplicationSlot internal constructor(private val component: Component) : Componentt by component, Area by AreaImpl(component) {
    var name by component.delegate(null as String?)
    var slotId by component.delegate("slot_${nextSlotId++}")
    var supportedTypes by component.delegate(listOf(EMPTY), { it.split(" ").map { ComplicationType.valueOf(it) } }, { it.joinToString(" ") { it.name } })


    operator fun BoundingBox.unaryPlus() = this@ComplicationSlot.component.add(this)
    operator fun Complication.unaryPlus() = this@ComplicationSlot.component.add(this)

}

