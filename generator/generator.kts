
import java.awt.Color
import kotlin.math.roundToInt

////////// Utils //////////
val Color.asHex get() = "#%02x%02x%02x".format(red, green, blue)

typealias Range = ClosedFloatingPointRange<Double>

val FULL: Range = 0.0..1.0

///////////// Builder ///////////////

open class Generator {
    internal val builder: StringBuilder = StringBuilder()

    internal open fun build() = builder.toString()

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
}

internal val Range.range get() = endInclusive - start
internal val REPLACEMENT_VALUE_DO_NOT_USE = "$!@#@!$"

class WatchFaceText : Generator() {

    internal val expressions = mutableListOf<String>()

    fun expression(expression: String): String {
        expressions += expression
        return REPLACEMENT_VALUE_DO_NOT_USE
    }
}

private var nextSlotId = 0
private var nextName = 0

class WatchFaceScene(val fullWidth: Int, val fullHeight: Int) : Generator() {
    fun partText(horizontal: Range = FULL, vertical: Range = FULL, color: Color? = null, text: WatchFaceText.() -> String) {
        WatchFaceText().apply {
            val height = (vertical.range * fullHeight).roundToInt()
            tag(
                "PartText",
                "height" to height,
                "width" to (horizontal.range * fullWidth).roundToInt(),
                "x" to (horizontal.start * fullWidth).roundToInt(),
                "y" to (vertical.start * fullHeight).roundToInt()
            ) {
                tag("Text") {
                    tag(
                        "Font",
                        "family" to "SYNC_TO_DEVICE",
                        "size" to height,
                        color?.let { "color" to color.asHex }
                    ) {
                        tag("Template") {
                            WatchFaceText().apply {
                                cdata(text().replace("%", "%%").replace(REPLACEMENT_VALUE_DO_NOT_USE, "%s"))
                                expressions.forEach { tag("Parameter", "expression" to it) }
                            }.build().let { builder.append(it) }
                        }
                    }
                }
            }
        }.build().let { builder.append(it) }
    }

    fun complication(id: String? = null, horizontal: Range = FULL, vertical: Range = FULL, types: WatchFaceComplicationSlot.() -> Unit) {
        val slotId = id ?: nextSlotId++
        val complicationSlot = WatchFaceComplicationSlot((horizontal.range * fullWidth).roundToInt(), (vertical.range * fullHeight).roundToInt()).apply {
            types()
        }

        tag(
            "ComplicationSlot",
            "slotId" to slotId,
            "supportedTypes" to complicationSlot.types.joinToString(" "),
            "height" to (vertical.range * fullHeight).roundToInt(),
            "width" to (horizontal.range * fullWidth).roundToInt(),
            "x" to (horizontal.start * fullWidth).roundToInt(),
            "y" to (vertical.start * fullHeight).roundToInt()
        ) {
            complicationSlot.build().let { builder.append(it) }
        }
    }

    fun partDraw(horizontal: Range = FULL, vertical: Range = FULL, alphaNormal: Double? = null, alphaAmbient: Double? = null, shapes: PartDraw.() -> Unit) {
        tag(
            "PartDraw",
            "height" to (vertical.range * fullHeight).roundToInt(),
            "width" to (horizontal.range * fullWidth).roundToInt(),
            "x" to (horizontal.start * fullWidth).roundToInt(),
            "y" to (vertical.start * fullHeight).roundToInt(),
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
                fullWidth = (horizontal.range * fullWidth).roundToInt(),
                fullHeight = (vertical.range * fullHeight).roundToInt(),
            ).apply {
                shapes()
            }.build().let { builder.append(it) }
        }
    }

    fun group(name: String? = null, horizontal: Range = FULL, vertical: Range = FULL, scene: WatchFaceScene.() -> Unit) {
        val width = (horizontal.range * fullWidth).roundToInt()
        val height = (vertical.range * fullHeight).roundToInt()
        tag(
            "Group",
            "name" to (name ?: "group_${nextName++}"),
            "height" to height,
            "width" to width,
            "x" to (horizontal.start * fullWidth).roundToInt(),
            "y" to (vertical.start * fullHeight).roundToInt(),
        ) {
            WatchFaceScene(width, height)
                .apply { scene() }
                .build().let { builder.append(it) }
        }
    }

    fun condition(expressions: WatchFaceCondition.() -> Unit) {
        tag("Condition") {
            WatchFaceCondition(fullWidth, fullHeight)
                .apply { expressions() }
                .build().let { builder.append(it) }
        }
    }

    fun digitalClock(horizontal: Range = FULL, vertical: Range = FULL, clock: WatchFaceDigitalClock.() -> Unit) {
        val width = (horizontal.range * fullWidth).roundToInt()
        val height = (vertical.range * fullHeight).roundToInt()
        tag(
            "DigitalClock",
            "height" to height,
            "width" to width,
            "x" to (horizontal.start * fullWidth).roundToInt(),
            "y" to (vertical.start * fullHeight).roundToInt(),
        ) {
            WatchFaceDigitalClock(width, height)
                .apply { clock() }
                .build().let { builder.append(it) }
        }
    }
}

class WatchFaceDigitalClock(val fullWidth: Int, val fullHeight: Int) : Generator() {
    fun timeText(horizontal: Range = FULL, vertical: Range = FULL, color: Color? = null, format: () -> String) {
        val height = (vertical.range * fullHeight).roundToInt()
        tag(
            "TimeText",
            "height" to height,
            "width" to (horizontal.range * fullWidth).roundToInt(),
            "x" to (horizontal.start * fullWidth).roundToInt(),
            "y" to (vertical.start * fullHeight).roundToInt(),
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
        expressions += Expression(name ?: "expression_${nextName++}", expression, scene)
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
                }.build().let { builder.append(it) }
            }
        }

        return super.build()
    }
}

class PartDraw(val fullWidth: Int, val fullHeight: Int) : Generator() {
    fun roundRectangle(color: Color, horizontal: Range = FULL, vertical: Range = FULL, radiusX: Double = 0.5, radiusY: Double = 0.5) {
        tag(
            "RoundRectangle",
            "height" to (vertical.range * fullHeight).roundToInt(),
            "width" to (horizontal.range * fullWidth).roundToInt(),
            "x" to (horizontal.start * fullWidth).roundToInt(),
            "y" to (vertical.start * fullHeight).roundToInt(),
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
                .build().let { builder.append(it) }
        }
    }
}


fun WatchFace(height: Int, width: Int, scene: WatchFaceScene.() -> Unit) =
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