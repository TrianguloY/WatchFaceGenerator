package com.trianguloy.generator.wff.helpers

import kotlin.math.roundToInt

open class Size internal constructor(name: String, internal val parentWidth: Int, internal val parentHeight: Int) : Component(name) {
    constructor(name: String, parent: Size) : this(name, parent.width, parent.height)

    var height by delegate(parentHeight)
    var width by delegate(parentWidth)
}

open class Area internal constructor(name: String, width: Int, height: Int) : Size(name, width, height) {

    constructor(name: String, parent: Size) : this(name, parent.width, parent.height)

    var x by delegate(0)
    var y by delegate(0)


    // Move to a helper
    var horizontal: Pair<Int, Int>
        get() = x to (x + width)
        set(value) {
            x = value.first
            width = value.second - value.first
        }
    var vertical: Pair<Int, Int>
        get() = y to (y + height)
        set(value) {
            y = value.first
            height = value.second - value.first
        }

    val Double.sh get() = (this * parentWidth).roundToInt()
    val Double.sv get() = (this * parentHeight).roundToInt()
}