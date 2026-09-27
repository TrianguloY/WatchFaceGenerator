package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.WatchFace.ClipShape.CIRCLE
import com.trianguloy.generator.wff.helpers.Size

class WatchFace(width: Int, height: Int) : Size("WatchFace", width, height) {

    enum class ClipShape {
        NONE,
        CIRCLE,
        RECTANGLE,
    }

    var clipShape by delegate(CIRCLE, { ClipShape.valueOf(it) }, { it.name })


    operator fun plus(metadata: Metadata<*>) = apply { super + metadata }
    operator fun plus(scene: Scene) = apply { super + scene }
}