package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Variant
import com.trianguloy.generator.wff.helpers.Componentt
import kotlin.reflect.KProperty

fun <T> Componentt.ambient(property: KProperty<T>, value: T) {
    +Variant(property.name, value.toString()) {}
}