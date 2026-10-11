package com.trianguloy.generator.xml

fun Tag(name: String, init: Tag.() -> Unit) = Tag(name).apply { init() }

@TagDsl
open class Tag internal constructor(val name: String) : Element {
    internal val properties = mutableMapOf<String, String>()
    internal val content = mutableListOf<Element>()

    operator fun set(key: String, value: Any) = properties.set(key, value.toString())
    operator fun Element.unaryPlus() {
        this@Tag.content += this
    }

    override fun toString() = buildString {

        append("<$name")
        append(properties.map { (key, value) -> " $key=\"$value\"" }.joinToString(""))
        if (content.isEmpty()) {
            append("/>")
        } else {
            append(">")
            appendLine()
            append(content.joinToString("\n").prependIndent("  "))
            appendLine()
            append("</$name>")

        }
    }


}

@DslMarker
internal annotation class TagDsl
