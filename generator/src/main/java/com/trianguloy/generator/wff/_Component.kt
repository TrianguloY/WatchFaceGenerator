package com.trianguloy.generator.wff

import com.trianguloy.generator.xml._Element
import com.trianguloy.generator.xml._Tag
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class _Component(name: String) : _Element {
    internal val _tag = _Tag(name)
    fun <V> _delegate(default: V, getter: (String) -> V, setter: (V) -> String = { it.toString() }) = PropertyDelegateProvider<_Component, ReadWriteProperty<_Component, V>> { _, property ->
        _tag[property.name] = setter(default)
        object : ReadWriteProperty<_Component, V> {
            override operator fun getValue(thisRef: _Component, property: KProperty<*>): V {
                return _tag._properties[property.name]?.let { getter(it) } ?: default
            }

            override operator fun setValue(thisRef: _Component, property: KProperty<*>, value: V) {
                _tag._properties[property.name] = setter(value)
            }
        }
    }

    fun _delegate(default: String) = _delegate(default, { it }, { it })
    fun _delegate(default: Int) = _delegate(default, { it.toInt() }, { it.toString() })
    internal fun <E : Enum<E>> _delegate(default: E, clazz: Class<E>) = _delegate(default, { v -> clazz.enumConstants.firstOrNull { it.name == v } ?: default }, { it.name })

    operator fun plus(element: _Component) = apply { _tag + element }

    override fun toString() = _tag.toString()
}