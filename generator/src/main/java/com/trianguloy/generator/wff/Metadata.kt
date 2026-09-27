package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.Metadata.Key.CLOCK_TYPE
import com.trianguloy.generator.wff.Metadata.Key.PREVIEW_TIME
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty

sealed class Metadata<V> private constructor(key: Key, customDelegate: Metadata<V>.() -> PropertyDelegateProvider<_Component, ReadWriteProperty<_Component, V>>) : _Component("Metadata") {

    class ClockType(value: ClockTypeValues) : Metadata<ClockTypeValues>(
        CLOCK_TYPE,
        { _delegate(value, ClockTypeValues::class.java) })

    enum class ClockTypeValues {
        DIGITAL,
        ANALOG,
    }

    class PreviewTime(time: PreviewTimeValue) : Metadata<PreviewTimeValue>(PREVIEW_TIME, { _delegate(time, { PreviewTimeValue.fromString(it) }, { it.toString() }) })

    class PreviewTimeValue(val hours: Int, val minutes: Int, val seconds: Int) {

        override fun toString() = "%02d:%02d:%02d".format(hours, minutes, seconds)

        companion object {
            fun fromString(value: String) = value
                .split(":")
                .map { it.toInt() }
                .let { (h, m, s) -> PreviewTimeValue(h, m, s) }
        }
    }

    class StepGoal(dailyGoal: Int) : Metadata<Int>(CLOCK_TYPE, { _delegate(dailyGoal) })


    enum class Key {
        CLOCK_TYPE,
        PREVIEW_TIME,
        STEP_GOAL,
    }

    var key by _delegate(key, Key::class.java)
    var value by customDelegate()
}