import java.awt.Color
import java.io.File
import kotlin.math.roundToInt

typealias Range = ClosedFloatingPointRange<Double>

///////////// Utils ///////////////

val Color.asHex get() = "#%02x%02x%02x".format(red, green, blue)

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


///////////// Design ///////////////

print("Generating")

val content = WatchFace(height = 450, width = 450)
{
    comment("Date 1")
    partText(
        horizontal = 0.0..1.0,
        vertical = 0.05..0.1,
    ) {
        "${expression("[YEAR]")}/${expression("[MONTH_Z]")}/${expression("[DAY]")}"
    }

    comment("Date 2")
    partText(
        horizontal = 0.0..1.0,
        vertical = 0.1..0.15
    ) {
        "${expression("[MONTH_F]")} - ${expression("[DAY_OF_WEEK_F]")}"
    }

    comment("Watch battery")
    partText(
        horizontal = 0.2..0.5,
        vertical = 0.2..0.25
    ) {
        "⌚${expression("[BATTERY_PERCENT]")}%"
    }

    comment("Phone Battery")
    complication(
        id = "1",
        horizontal = 0.5..0.8,
        vertical = 0.2..0.25,
    ) {
        ofType("SHORT_TEXT") {
            partText {
                "📱 ${expression("[COMPLICATION.TEXT]")}%"
            }
        }
    }

    comment("AOD")
    partDraw(alphaNormal = 0.0, alphaAmbient = 0.5) {
        roundRectangle(Color.BLACK)
    }

    comment("Notification counter")
    group(
        horizontal = 0.9..0.98,
        vertical = 0.46..0.54
    ) {
        condition {
            expression("[UNREAD_NOTIFICATION_COUNT] >= 0") {
                partDraw {
                    roundRectangle(Color.WHITE)
                }
                partText(color = Color.BLACK) {
                    expression("[UNREAD_NOTIFICATION_COUNT]+1")
                }
            }
        }
    }

    comment("Time")
    digitalClock(
        horizontal = 0.5..0.9,
        vertical = 0.15..0.85
    ) {
        comment("Hour")
        timeText(vertical = 0.0..0.55) { "hh" }

        comment("Minutes")
        timeText(vertical = 0.45..1.0) { "mm" }
    }

}

val content2 = buildString {
    append(
        """
<WatchFace
    clipShape="CIRCLE"
    height="450"
    width="450">
    <Metadata
        key="CLOCK_TYPE"
        value="DIGITAL" />
    <Scene>

        <!-- Date 1 -->
        <PartText
            height="20"
            width="450"
            x="0"
            y="20">
            <Text>
                <Font
                    family="SYNC_TO_DEVICE"
                    size="20">
                    <Template>
                        <![CDATA[%s/%s/%s]]>
                        <Parameter expression="[YEAR]" />
                        <Parameter expression="[MONTH_Z]" />
                        <Parameter expression="[DAY]" />
                    </Template>
                </Font>
            </Text>
        </PartText>
        <!-- Date 2 -->
        <PartText
            height="20"
            width="450"
            x="0"
            y="40">
            <Text>
                <Font
                    family="SYNC_TO_DEVICE"
                    size="20">
                    <Template>
                        <![CDATA[%s - %s]]>
                        <Parameter expression="[MONTH_F]" />
                        <Parameter expression="[DAY_OF_WEEK_F]" />
                    </Template>
                </Font>
            </Text>
        </PartText>

        <!-- Watch battery -->
        <PartText
            height="20"
            width="165"
            x="60"
            y="70">
            <Text>
                <Font
                    family="SYNC_TO_DEVICE"
                    size="20">
                    <Template>
                        <![CDATA[⌚ %s%%]]>
                        <Parameter expression="[BATTERY_PERCENT]" />
                    </Template>
                </Font>
            </Text>
        </PartText>

        <!-- Phone Battery -->
        <ComplicationSlot
            height="20"
            slotId="1"
            supportedTypes="EMPTY SHORT_TEXT"
            width="165"
            x="225"
            y="70">
            <Complication type="SHORT_TEXT">
                <PartText
                    height="20"
                    width="165"
                    x="0"
                    y="0">
                    <Text>
                        <Font
                            family="SYNC_TO_DEVICE"
                            size="20">
                            <Template>
                                <![CDATA[📱 %s%%]]>
                                <Parameter expression="[COMPLICATION.TEXT]" />
                            </Template>
                        </Font>
                    </Text>
                </PartText>
            </Complication>
        </ComplicationSlot>


        <!-- AOD -->
        <PartDraw
            alpha="0"
            height="450"
            width="450"
            x="0"
            y="0">
            <RoundRectangle
                cornerRadiusX="225"
                cornerRadiusY="225"
                height="450"
                width="450"
                x="0"
                y="0">
                <Fill color="#000000" />
            </RoundRectangle>
            <Variant
                mode="AMBIENT"
                target="alpha"
                value="128" />
        </PartDraw>

        <!-- Notification counter -->
        <Group
            name="unread_notifications"
            height="50"
            width="50"
            x="390"
            y="200">
            <Condition>
                <Expressions>
                    <Expression name="notifications">
                        <![CDATA[ [UNREAD_NOTIFICATION_COUNT] > 0  ]]>
                    </Expression>
                </Expressions>
                <Compare expression="notifications">
                    <PartDraw
                        height="50"
                        width="50"
                        x="0"
                        y="0">
                        <RoundRectangle
                            cornerRadiusX="25"
                            cornerRadiusY="25"
                            height="50"
                            width="50"
                            x="0"
                            y="0">
                            <Fill color="#FFFFFF" />
                        </RoundRectangle>
                    </PartDraw>
                    <PartText
                        height="50"
                        width="50"
                        x="0"
                        y="0">
                        <Text>
                            <Font
                                color="#000000"
                                family="SYNC_TO_DEVICE"
                                size="50">
                                <Template>
                                    <![CDATA[%s]]>
                                    <Parameter expression="[UNREAD_NOTIFICATION_COUNT]" />
                                </Template>
                            </Font>
                        </Text>
                    </PartText>
                </Compare>
            </Condition>
        </Group>

        <!-- Time -->
        <DigitalClock
            height="450"
            width="225"
            x="225"
            y="0">
            <!-- HOUR -->
            <TimeText
                align="START"
                format="hh"
                height="150"
                width="225"
                x="0"
                y="75">
                <Font
                    color="#ffffffff"
                    family="SYNC_TO_DEVICE"
                    size="150" />
            </TimeText>

            <!-- MINUTE -->
            <TimeText
                align="START"
                format="mm"
                height="150"
                width="225"
                x="0"
                y="225">
                <Font
                    color="#ffffffff"
                    family="SYNC_TO_DEVICE"
                    size="150" />
            </TimeText>
        </DigitalClock>
    </Scene>
</WatchFace>
    """
    )
}

File("../watchface/src/main/res/raw", "watchface.xml").writeText(content)
print("Generated")
