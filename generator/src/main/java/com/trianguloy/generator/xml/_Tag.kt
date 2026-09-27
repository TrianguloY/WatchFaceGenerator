package com.trianguloy.generator.xml

open class _Tag(val _name: String) : _Element {
    val _properties = mutableMapOf<String, String>()
    val _content = mutableListOf<_Element>()

    operator fun set(key: String, value: Any) = _properties.set(key, value.toString())
    operator fun plus(element: _Element) = apply { _content += element }

    override fun toString() = buildString {

        append("<$_name")
        append(_properties.map { (key, value) -> " $key=\"$value\"" }.joinToString(""))
        if (_content.isEmpty()) {
            append("/>")
        } else {
            append(">")
            appendLine()
            append(_content.joinToString("\n").prependIndent("  "))
            appendLine()
            append("</$_name>")

        }
    }
}
