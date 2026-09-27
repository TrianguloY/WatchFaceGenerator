package com.trianguloy.generator.xml

internal open class Tag(val name: String) : Element {
    internal val properties = mutableMapOf<String, String>()
    internal val content = mutableListOf<Element>()

    internal operator fun set(key: String, value: Any) = properties.set(key, value.toString())
    internal operator fun Element.unaryPlus() {
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
