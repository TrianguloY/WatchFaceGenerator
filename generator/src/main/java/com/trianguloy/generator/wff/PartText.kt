package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

class PartText internal constructor(parentComponent: Size) : Area("PartText", parentComponent) {

}

fun <C : Size> C.PartText() = PartText(this)