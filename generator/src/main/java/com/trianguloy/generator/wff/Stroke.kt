package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import java.awt.Color.WHITE

class Stroke : Component("Stroke") {
    var color by delegate(WHITE)
    var thickness by delegate(1)
    var dashIntervals by delegate(null as List<Double>?)
}