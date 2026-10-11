package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Fill
import com.trianguloy.generator.wff.RoundRectangle
import com.trianguloy.generator.wff.Stroke
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.RectangleFill(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillInit: Fill.() -> Unit = {}) = RoundRectangle {
    roundRectangleInit()
    +Fill {
        fillInit()
    }
}

fun Componentt.ElipseFill(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillInit: Fill.() -> Unit = {}) = RoundRectangle {
    cornerRadiusX = width / 2
    cornerRadiusY = height / 2
    roundRectangleInit()
    +Fill {
        fillInit()
    }
}

fun Componentt.RectangleBorder(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillStroke: Stroke.() -> Unit = {}) = RoundRectangle {
    roundRectangleInit()
    +Stroke {
        fillStroke()
    }
}

fun Componentt.ElipseBorder(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillStroke: Stroke.() -> Unit = {}) = RoundRectangle {
    cornerRadiusX = width / 2
    cornerRadiusY = height / 2
    roundRectangleInit()
    +Stroke {
        fillStroke()
    }
}