package com.trianguloy.generator.wff.helpers

import com.trianguloy.generator.wff.BoundingBox
import com.trianguloy.generator.wff.Complication
import com.trianguloy.generator.wff.ComplicationSlot
import com.trianguloy.generator.wff.ComplicationType
import com.trianguloy.generator.wff.ComplicationType.EMPTY

fun Componentt.SimpleComplication(complicationSlotInit: ComplicationSlot.() -> Unit = {}, boundingBoxInit: BoundingBox.() -> Unit = {}, complication: SimpleComplication.() -> Unit = {}) = ComplicationSlot().apply {
    complicationSlotInit()
    +BoundingBox {
        boundingBoxInit()
    }
    val simpleComplication = SimpleComplication(this)
    simpleComplication.complication()
    supportedTypes = simpleComplication.complications.map { it.type } + EMPTY
    for (complication in simpleComplication.complications) {
        +complication
    }
}

class SimpleComplication internal constructor(private val parent: Componentt) {
    internal val complications = mutableListOf<Complication>()
    fun forType(type: ComplicationType, scene: Complication.() -> Unit = {}) {
        complications += parent.Complication(type) { scene() }
    }
}