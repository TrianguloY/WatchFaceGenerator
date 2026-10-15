package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Complication(type: ComplicationType, init: Complication.() -> Unit = {}) = Complication(type, Component("Complication", this)).apply { init() }
class Complication internal constructor(type: ComplicationType, private val component: Component) : Componentt by component {
    var type by component.delegate(type, ComplicationType::class)

    operator fun PartImage.unaryPlus() = this@Complication.component.add(this)
    operator fun PartText.unaryPlus() = this@Complication.component.add(this)
    operator fun Condition.unaryPlus() = this@Complication.component.add(this)
}

enum class ComplicationType {
    EMPTY,
    SHORT_TEXT,
    LONG_TEXT,
    PHOTO_IMAGE,
    SMALL_IMAGE,
    WEIGHTED_ELEMENTS,
}