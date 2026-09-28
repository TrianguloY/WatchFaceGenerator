package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size


fun <S : Size> S.PartImage() = PartImage(this)
class PartImage internal constructor(parent: Size) : Area("PartImage", parent) {

    operator fun Image.unaryPlus() = super.add(this)
}