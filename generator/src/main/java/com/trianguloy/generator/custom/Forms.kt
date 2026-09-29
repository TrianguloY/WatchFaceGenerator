package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Fill
import com.trianguloy.generator.wff.RoundRectangle
import com.trianguloy.generator.wff.helpers.Size

fun Size.Rectangle(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillInit: Fill.() -> Unit = {}) = RoundRectangle().apply {
    roundRectangleInit()
    +Fill().apply {
        fillInit()
    }
}

fun Size.Elipse(roundRectangleInit: RoundRectangle.() -> Unit = {}, fillInit: Fill.() -> Unit = {}) = RoundRectangle().apply {
    cornerRadiusX = width / 2
    cornerRadiusY = height / 2
    roundRectangleInit()
    +Fill().apply {
        fillInit()
    }
}