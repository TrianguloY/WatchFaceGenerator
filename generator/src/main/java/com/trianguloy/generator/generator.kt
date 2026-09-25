package com.trianguloy.generator

import java.awt.Color
import java.io.File
import kotlin.math.roundToInt


// global

private var nextSlotId = 0
private var nextGroup = 0
private var nextExpression = 0

internal const val REPLACEMENT_VALUE_DO_NOT_USE = "$!@#@!$"

///////////// Builder ///////////////

open class Generator {
    internal val builder: StringBuilder = StringBuilder()

    open fun build() = builder.toString()

    fun comment(comment: String) {
        with(builder) {
            append("<!-- $comment -->")
            appendLine()
        }
    }

    fun tag(tag: String, vararg attributes: Pair<String, Any>?, content: (Generator.() -> Unit)? = null) {
        builder.apply {
            append("<$tag")
            for ((key, value) in attributes.filterNotNull()) {
                append(" $key=\"$value\"")
            }
            if (content != null) {
                append(">")
                appendLine()
                content()
                append("</$tag>")
            } else {
                append("/>")
            }
            appendLine()
        }
    }

    fun cdata(text: String) {
        with(builder) {
            append("<![CDATA[")
            append(text)
            append("]]>")
            appendLine()
        }
    }

    fun Generator.add() {
        this.build().let { this@Generator.builder.append(it) }
    }
}


class WatchFaceText : Generator() {

    internal val expressions = mutableListOf<String>()

    fun expression(expression: String): String {
        expressions += expression
        return REPLACEMENT_VALUE_DO_NOT_USE
    }
}


class WatchFaceScene(val fullWidth: Int, val fullHeight: Int) : Generator() {
    fun partText(horizontal: Range = FULL, vertical: Range = FULL, color: Color? = null, text: WatchFaceText.() -> String) {
        val (x, width) = horizontal * fullWidth
        val (y, height) = vertical * fullHeight

        WatchFaceText().apply {
            tag(
                "PartText",
                "x" to x, "y" to y,
                "width" to width, "height" to height,
            ) {
                tag("Text") {
                    tag(
                        "Font",
                        "family" to "SYNC_TO_DEVICE",
                        "size" to height,
                        color?.let { "color" to color.asHex }
                    ) {
                        tag("Template") {
                            cdata(text().replace("%", "%%").replace(REPLACEMENT_VALUE_DO_NOT_USE, "%s"))
                            expressions.forEach { tag("Parameter", "expression" to it) }
                        }
                    }
                }
            }
        }.add()
    }

    fun partImage(
        horizontal: Range = FULL, vertical: Range = FULL, image: String
    ) {
        val (x, width) = horizontal * fullWidth
        val (y, height) = vertical * fullHeight
        tag(
            "PartImage",
            "x" to x, "y" to y,
            "width" to width, "height" to height,
        ) {
            tag(
                "Image",
                "resource" to image
            )
        }
    }

    fun complication(id: String? = null, name: String? = null, horizontal: Range = FULL, vertical: Range = FULL, types: WatchFaceComplicationSlot.() -> Unit) {
        val (x, width) = horizontal * fullWidth
        val (y, height) = vertical * fullHeight

        val slotId = id ?: nextSlotId++
        val complicationSlot = WatchFaceComplicationSlot(width, height).apply {
            types()
        }

        tag(
            "ComplicationSlot",
            "slotId" to slotId,
            "name" to (name ?: "Slot $slotId"),
            "supportedTypes" to complicationSlot.types.joinToString(" "),
            "x" to x, "y" to y,
            "width" to width, "height" to height,
        ) {
            tag(
                "BoundingBox",
                "x" to 0, "y" to 0,
                "width" to width, "height" to height,
            )
            complicationSlot.add()
        }
    }

    fun partDraw(horizontal: Range = FULL, vertical: Range = FULL, alphaNormal: Double? = null, alphaAmbient: Double? = null, shapes: PartDraw.() -> Unit) {
        val (x, width) = horizontal * fullWidth
        val (y, height) = vertical * fullHeight

        tag(
            "PartDraw",
            "x" to x, "y" to y,
            "width" to width, "height" to height,
            alphaNormal?.let { "alpha" to (it * 255).roundToInt() },
        ) {
            if (alphaAmbient != null) {
                tag(
                    "Variant",
                    "mode" to "AMBIENT",
                    "target" to "alpha",
                    "value" to (alphaAmbient * 255).roundToInt(),
                )
            }
            PartDraw(
                fullWidth = width,
                fullHeight = height,
            ).apply {
                shapes()
            }.add()
        }
    }

    fun group(name: String? = null, horizontal: Range = FULL, vertical: Range = FULL, scene: WatchFaceScene.() -> Unit) {
        val (x, width) = horizontal * fullWidth
        val (y, height) = vertical * fullHeight

        tag(
            "Group",
            "name" to (name ?: "group_${nextGroup++}"),
            "x" to x, "y" to y,
            "width" to width, "height" to height,
        ) {
            WatchFaceScene(width, height)
                .apply { scene() }
                .add()
        }
    }

    fun condition(expressions: WatchFaceCondition.() -> Unit) {
        tag("Condition") {
            WatchFaceCondition(fullWidth, fullHeight)
                .apply { expressions() }
                .add()
        }
    }

    fun digitalClock(horizontal: Range = FULL, vertical: Range = FULL, clock: WatchFaceDigitalClock.() -> Unit) {
        val (x, width) = horizontal * fullWidth
        val (y, height) = vertical * fullHeight

        tag(
            "DigitalClock",
            "x" to x, "y" to y,
            "width" to width, "height" to height,
        ) {
            WatchFaceDigitalClock(width, height)
                .apply { clock() }
                .add()
        }
    }
}

class WatchFaceDigitalClock(val fullWidth: Int, val fullHeight: Int) : Generator() {
    fun timeText(horizontal: Range = FULL, vertical: Range = FULL, color: Color? = null, format: () -> String) {
        val (x, width) = horizontal * fullWidth
        val (y, height) = vertical * fullHeight

        tag(
            "TimeText",
            "x" to x, "y" to y,
            "width" to width, "height" to height,
            "format" to format()
        ) {
            tag(
                "Font",
                "family" to "SYNC_TO_DEVICE",
                "size" to height,
                color?.let { "color" to color.asHex }
            )
        }
    }
}

class WatchFaceCondition(val fullWidth: Int, val fullHeight: Int) : Generator() {
    private data class Expression(val name: String, val check: String, val then: WatchFaceScene.() -> Unit)

    private val expressions = mutableListOf<Expression>()

    fun expression(expression: String, name: String? = null, scene: WatchFaceScene.() -> Unit) {
        expressions += Expression(name ?: "expression_${nextExpression++}", expression, scene)
    }

    override fun build(): String {
        tag("Expressions") {
            expressions.forEach { expression ->
                tag(
                    "Expression",
                    "name" to expression.name
                ) {
                    cdata(expression.check)
                }
            }
        }
        expressions.forEach { expression ->
            tag("Compare", "expression" to expression.name) {
                WatchFaceScene(fullWidth, fullHeight).apply {
                    expression.then(this) // what black magic is this...
                }.add()
            }
        }

        return super.build()
    }
}

class PartDraw(val fullWidth: Int, val fullHeight: Int) : Generator() {
    fun roundRectangle(color: Color, horizontal: Range = FULL, vertical: Range = FULL, radiusX: Double = 0.5, radiusY: Double = 0.5) {
        val (x, width) = horizontal * fullWidth
        val (y, height) = vertical * fullHeight

        tag(
            "RoundRectangle",
            "x" to x, "y" to y,
            "width" to width, "height" to height,
            "cornerRadiusX" to (radiusX * horizontal.range * fullWidth).roundToInt(),
            "cornerRadiusY" to (radiusY * vertical.range * fullWidth).roundToInt(),
        ) {
            tag("Fill", "color" to color.asHex)
        }
    }
}

class WatchFaceComplicationSlot(val fullWidth: Int, val fullHeight: Int) : Generator() {
    internal val types = mutableListOf("EMPTY")
    fun ofType(type: String, scene: WatchFaceScene.() -> Unit) {
        types += type
        tag("Complication", "type" to type) {
            WatchFaceScene(fullWidth, fullHeight)
                .apply { scene() }
                .add()
        }
    }
}


fun WatchFace(height: Int, width: Int, scene: WatchFaceScene.() -> Unit) {
    println("Generating")

    WatchFaceScene(width, height).apply {
        comment("Generated by TrianguloY watch face generator")
        tag(
            "WatchFace",
            "clipShape" to "CIRCLE",
            "height" to height, "width" to width
        ) {
            tag(
                "Metadata",
                "key" to "CLOCK_TYPE",
                "value" to "DIGITAL"
            )
            tag("Scene") {
                scene()
            }
        }
    }.build()
        .let { File("watchface/src/main/res/raw", "watchface.xml").writeText(it) }

    println("Generated")
}