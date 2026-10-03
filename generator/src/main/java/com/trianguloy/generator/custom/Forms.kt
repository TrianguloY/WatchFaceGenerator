package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Fill
import com.trianguloy.generator.wff.RoundRectangle
import com.trianguloy.generator.wff.Stroke
import com.trianguloy.generator.wff.helpers.Size

fun Size.RectangleFill(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillInit: Fill.() -> Unit = {}) = RoundRectangle().apply {
    roundRectangleInit()
    +Fill().apply {
        fillInit()
    }
}

fun Size.ElipseFill(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillInit: Fill.() -> Unit = {}) = RoundRectangle().apply {
    cornerRadiusX = width / 2
    cornerRadiusY = height / 2
    roundRectangleInit()
    +Fill().apply {
        fillInit()
    }
}

fun Size.RectangleBorder(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillStroke: Stroke.() -> Unit = {}) = RoundRectangle().apply {
    roundRectangleInit()
    +Stroke().apply {
        fillStroke()
    }
}

fun Size.ElipseBorder(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillStroke: Stroke.() -> Unit = {}) = RoundRectangle().apply {
    cornerRadiusX = width / 2
    cornerRadiusY = height / 2
    roundRectangleInit()
    +Stroke().apply {
        fillStroke()
    }
}