package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

fun <S : Size> S.BoundingBox() = BoundingBox(this)
class BoundingBox internal constructor(parent: Size) : Area("BoundingBox", parent)