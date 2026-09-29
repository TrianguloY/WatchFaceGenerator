package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.PartDraw
import com.trianguloy.generator.wff.helpers.Size

// remove if dsl constructors are added to the wff themselves
fun Size.SimpleDraw(partDrawInit: PartDraw.() -> Unit) = PartDraw().apply { partDrawInit() }