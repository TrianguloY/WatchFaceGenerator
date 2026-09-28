package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.ComplicationType.EMPTY
import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

private var nextSlotId = 0

fun <S : Size> S.ComplicationSlot() = ComplicationSlot(this)
class ComplicationSlot internal constructor(parent: Size) : Area("ComplicationSlot", parent) {
    var name by delegate(null as String?)
    var slotId by delegate("slot_${nextSlotId++}")
    var supportedTypes by delegate(listOf(EMPTY), { it.split(" ").map { ComplicationType.valueOf(it) } }, { it.joinToString(" ") { it.name } })


    operator fun BoundingBox.unaryPlus() = super.add(this)
    operator fun Complication.unaryPlus() = super.add(this)

}

enum class ComplicationType {
    EMPTY,
    SHORT_TEXT,
}
