package com.trianguloy.generator.wff.helpers

import com.trianguloy.generator.wff.Variant
import com.trianguloy.generator.xml.Cdata
import com.trianguloy.generator.xml.Comment
import com.trianguloy.generator.xml.Tag
import java.awt.Color
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@DslMarker
internal annotation class ComponentDsl

@ComponentDsl
interface Componentt {
    val tag: Tag
    val parent: Componentt?

    // always allowed
    operator fun Comment.unaryPlus()
    operator fun Variant.unaryPlus() // allow everywhere for now

    override fun toString(): String
}

open class Component(name: String, override val parent: Componentt?) : Componentt {
    override val tag = Tag(name)
    internal fun <V : Any> delegate(default: V?, getter: (String) -> V, setter: (V) -> String = { it.toString() }) =
        object : ReadWriteProperty<Any, V?> {
            override operator fun getValue(thisRef: Any, property: KProperty<*>): V? {
                return tag.properties[property.name]?.let { getter(it) } ?: default
            }

            override operator fun setValue(thisRef: Any, property: KProperty<*>, value: V?) {
                if (value == null) {
                    if (default == null) {
                        tag.properties.remove(property.name)
                    } else {
                        tag.properties[property.name] = setter(default)
                    }
                } else {
                    tag.properties[property.name] = setter(value)
                }
            }
        }

    internal fun <V : Any> delegate(default: V, getter: (String) -> V, setter: (V) -> String) = PropertyDelegateProvider<Any, ReadWriteProperty<Any, V>> { _, property ->
        tag[property.name] = setter(default)
        object : ReadWriteProperty<Any, V> {
            override operator fun getValue(thisRef: Any, property: KProperty<*>): V {
                return tag.properties[property.name]?.let { getter(it) } ?: default
            }

            override operator fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
                tag.properties[property.name] = setter(value)
            }
        }
    }


    internal fun delegate(default: String) = delegate(default, { it }, { it })
    internal fun delegate(default: String?) = delegate(default, { it }, { it })
    internal fun delegate(default: Int) = delegate(default, { it.toInt() }, { it.toString() })
    internal fun delegate(default: Int?) = delegate(default, { it.toInt() }, { it.toString() })
    internal fun delegate(default: Color) = delegate(default, { Color.decode(it) }, { "#%02x%02x%02x".format(it.red, it.green, it.blue) })
    internal fun delegate(default: Color? = null) = delegate<Color>(default, { Color.decode(it) }, { "#%02x%02x%02x".format(it.red, it.green, it.blue) })
    internal fun <E : Enum<E>> delegate(default: E, clazz: KClass<E>) = delegate(default, { v -> clazz.java.enumConstants.firstOrNull { it.name == v } ?: default }, { it.name })
    internal fun <E : Enum<E>> delegate(default: E?, clazz: KClass<E>) = delegate(default, { v -> clazz.java.enumConstants.firstOrNull { it.name == v }!!/*throw*/ }, { it.name })
    internal fun delegate(default: List<Double>?) = delegate(default, { v -> v.split(" ").map { it.toDouble() } }, { it.joinToString(" ") })


    // not as operator to avoid recursion problems
    internal fun add(componentt: Componentt) = tag.run { +componentt.tag }

    // not always allowed
    internal fun add(cdata: Cdata) = tag.apply { +cdata }


    // always allowed
    override operator fun Comment.unaryPlus() = tag.run { +this@unaryPlus }

    // may not always be allowed, but for now just allow
    override operator fun Variant.unaryPlus() = this@Component.add(this)
    override fun toString() = tag.toString()
}