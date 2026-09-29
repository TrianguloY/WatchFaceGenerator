package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Image
import com.trianguloy.generator.wff.PartImage
import com.trianguloy.generator.wff.helpers.Size

fun Size.SimpleImage(partImageInit: PartImage.() -> Unit, resource: String) = PartImage().apply {
    partImageInit()
    Image(resource)
}