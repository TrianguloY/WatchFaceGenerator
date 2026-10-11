package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.WatchFace.ClipShape.CIRCLE
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt
import com.trianguloy.generator.wff.helpers.Size
import com.trianguloy.generator.wff.helpers.SizeImpl

fun WatchFace(width: Int, height: Int, init: WatchFace.() -> Unit = {}) = WatchFace(width, height, Component("WatchFace", null)).apply { init() }


class WatchFace internal constructor(width: Int, height: Int, private val component: Component) : Componentt by component, Size by SizeImpl(component, width, height) {

    enum class ClipShape {
        NONE,
        CIRCLE,
        RECTANGLE,
    }

    var clipShape by component.delegate(CIRCLE, { ClipShape.valueOf(it) }, { it.name })


    operator fun Metadata<*>.unaryPlus() = this@WatchFace.component.add(this)
    operator fun Scene.unaryPlus() = this@WatchFace.component.add(this)
}