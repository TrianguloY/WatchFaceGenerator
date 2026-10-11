package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.Image(resource: String, init: Image.() -> Unit = {}) = Image(resource, Component("Image", this)).apply { init() }
class Image internal constructor(resource: String, private val component: Component) : Componentt by component {
    var resource by component.delegate(resource)
}