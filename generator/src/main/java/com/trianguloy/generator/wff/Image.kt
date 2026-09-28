package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Image(resource: String) : Component("Image") {
    var resource by delegate(resource)
}