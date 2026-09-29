package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.Metadata.Key.CLOCK_TYPE
import com.trianguloy.generator.wff.Metadata.Key.PREVIEW_TIME
import com.trianguloy.generator.wff.helpers.Component
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty

sealed class Metadata<V> private constructor(key: Key, customDelegate: Metadata<V>.() -> PropertyDelegateProvider<Component, ReadWriteProperty<Component, V>>) : Component("Metadata") {

    class ClockType(value: ClockTypeValues) : Metadata<ClockTypeValues>(
        CLOCK_TYPE,
        { delegate(value, ClockTypeValues::class) })

    enum class ClockTypeValues {
        DIGITAL,
        ANALOG,
    }

    class PreviewTime(time: PreviewTimeValue) : Metadata<PreviewTimeValue>(PREVIEW_TIME, { delegate(time, { PreviewTimeValue.fromString(it) }, { it.toString() }) })

    class PreviewTimeValue(val hours: Int, val minutes: Int, val seconds: Int) {

        override fun toString() = "%02d:%02d:%02d".format(hours, minutes, seconds)

        companion object {
            internal fun fromString(value: String) = value
                .split(":")
                .map { it.toInt() }
                .let { (h, m, s) -> PreviewTimeValue(h, m, s) }
        }
    }

    class StepGoal(dailyGoal: Int) : Metadata<Int>(CLOCK_TYPE, { delegate(dailyGoal) })


    enum class Key {
        CLOCK_TYPE,
        PREVIEW_TIME,
        STEP_GOAL,
    }

    internal var key by delegate(key, Key::class)
    var value by customDelegate()
}