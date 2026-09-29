package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Area
import com.trianguloy.generator.wff.helpers.Size

fun Size.BoundingBox() = BoundingBox(this)
class BoundingBox internal constructor(parent: Size) : Area("BoundingBox", parent)