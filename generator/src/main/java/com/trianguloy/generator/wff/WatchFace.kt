package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.WatchFace.ClipShape.CIRCLE

class WatchFace(height: Int, width: Int) : _Component("WatchFace") {

    enum class ClipShape {
        NONE,
        CIRCLE,
        RECTANGLE,
    }

    var clipShape by _delegate(CIRCLE, { ClipShape.valueOf(it) }, { it.name })
    var height by _delegate(height)
    var width by _delegate(width)


    operator fun plus(metadata: Metadata<*>) = apply { super + metadata }
    operator fun plus(scene: Scene) = apply { super + scene }
}