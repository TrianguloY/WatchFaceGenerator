package com.trianguloy.generator

import java.awt.Color
import kotlin.math.roundToInt

/** Color to hex representation */
val Color.asHex get() = "#%02x%02x%02x".format(red, green, blue)

/** A range where the sides are doubles. */
typealias Range = ClosedFloatingPointRange<Double>

/** Returns the actual range (end-start) */
internal val Range.range get() = endInclusive - start

/** Returns a pair (from,range) of this range scaled an [amount] */
internal operator fun Range.times(amount: Int) = (start * amount).roundToInt() to (range * amount).roundToInt()

/** A full Range */
val FULL: Range = 0.0..1.0