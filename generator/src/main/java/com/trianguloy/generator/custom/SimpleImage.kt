package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Image
import com.trianguloy.generator.wff.PartImage
import com.trianguloy.generator.wff.helpers.Componentt

fun Componentt.SimpleImage(partImageInit: PartImage.() -> Unit = {}, resource: String) = PartImage {
    partImageInit()
    +Image(resource)
}