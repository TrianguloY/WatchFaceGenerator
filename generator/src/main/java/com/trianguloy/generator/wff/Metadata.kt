package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.Metadata.ClockTypeValues.DIGITAL
import com.trianguloy.generator.wff.Metadata.Key.CLOCK_TYPE
import com.trianguloy.generator.wff.Metadata.Key.PREVIEW_TIME
import com.trianguloy.generator.wff.Metadata.Key.STEP_GOAL
import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.wff.helpers.Componentt
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty


class Metadata<V> private constructor(key: Key, customDelegate: Metadata<V>.() -> PropertyDelegateProvider<Any, ReadWriteProperty<Any, V>>, private val component: Component) : Componentt by component {

    companion object {
        fun Componentt.Metadata_ClockType(init: Metadata<ClockTypeValues>.() -> Unit = {}) = Metadata(
            CLOCK_TYPE,
            { component.delegate(DIGITAL, ClockTypeValues::class) },
            Component("Metadata", this)
        ).apply { init() }

        fun Componentt.Metadata_PreviewTime(init: Metadata<PreviewTimeValue>.() -> Unit = {}) = Metadata(
            PREVIEW_TIME,
            { component.delegate(PreviewTimeValue(10, 10, 20), { PreviewTimeValue.fromString(it) }, { it.toString() }) },
            Component("Metadata", this)
        ).apply { init() }

        fun Componentt.Metadata_StepGoal(init: Metadata<Int>.() -> Unit = {}) = Metadata(
            STEP_GOAL,
            { component.delegate(1000) },
            Component("Metadata", this)
        ).apply { init() }
    }

    enum class ClockTypeValues {
        DIGITAL,
        ANALOG,
    }

    class PreviewTimeValue(val hours: Int, val minutes: Int, val seconds: Int) {

        override fun toString() = "%02d:%02d:%02d".format(hours, minutes, seconds)

        companion object {
            internal fun fromString(value: String) = value
                .split(":")
                .map { it.toInt() }
                .let { (h, m, s) -> PreviewTimeValue(h, m, s) }
        }
    }


    enum class Key {
        CLOCK_TYPE,
        PREVIEW_TIME,
        STEP_GOAL,
    }

    internal var key by component.delegate(key, Key::class)
    var value by customDelegate()
}