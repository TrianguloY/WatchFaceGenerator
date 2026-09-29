package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Variant
import com.trianguloy.generator.wff.helpers.Component
import kotlin.reflect.KProperty

fun <T> Component.ambient(property: KProperty<T>, value: T) {
    +Variant(property.name, value.toString())
}