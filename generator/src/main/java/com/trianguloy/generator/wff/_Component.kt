package com.trianguloy.generator.wff

import com.trianguloy.generator.xml._Comment
import com.trianguloy.generator.xml._Element
import com.trianguloy.generator.xml._Tag
import java.awt.Color
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class _Component(name: String) : _Element {
    internal val _tag = _Tag(name)
    internal fun <V : Any> _delegate(default: V?, getter: (String) -> V, setter: (V) -> String = { it.toString() }) =
        object : ReadWriteProperty<_Component, V?> {
            override operator fun getValue(thisRef: _Component, property: KProperty<*>): V? {
                return _tag._properties[property.name]?.let { getter(it) } ?: default
            }

            override operator fun setValue(thisRef: _Component, property: KProperty<*>, value: V?) {
                if (value == null) {
                    if (default == null) {
                        _tag._properties.remove(property.name)
                    } else {
                        _tag._properties[property.name] = setter(default)
                    }
                } else {
                    _tag._properties[property.name] = setter(value)
                }
            }
        }

    internal fun <V : Any> _delegate(default: V, getter: (String) -> V, setter: (V) -> String) = PropertyDelegateProvider<_Component, ReadWriteProperty<_Component, V>> { _, property ->
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


    internal fun _delegate(default: String) = _delegate(default, { it }, { it })
    internal fun _delegate(default: Int) = _delegate(default, { it.toInt() }, { it.toString() })
    internal fun _delegate(default: Color) = _delegate(default, { Color.decode(it) }, { "#%02x%02x%02x".format(it.red, it.green, it.blue) })
    internal fun _delegate(default: Color? = null) = _delegate<Color>(default, { Color.decode(it) }, { "#%02x%02x%02x".format(it.red, it.green, it.blue) })
    internal fun <E : Enum<E>> _delegate(default: E, clazz: Class<E>) = _delegate(default, { v -> clazz.enumConstants.firstOrNull { it.name == v } ?: default }, { it.name })

    internal operator fun plus(element: _Component) = apply { _tag + element }
    operator fun plus(comment: _Comment) = apply { _tag + comment }

    override fun toString() = _tag.toString()
}