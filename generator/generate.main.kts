import java.awt.Color
import java.io.File
import kotlin.math.roundToInt

typealias Range = ClosedFloatingPointRange<Double>


val FULL: Range = 0.0..1.0

print("Generating")


///////////// Builder ///////////////

open class Generator {
    internal val builder: StringBuilder = StringBuilder()

    internal fun build() = builder.toString()

    fun comment(comment: String) {
        with(builder) {
            append("<!-- $comment -->")
            appendLine()
        }
    }

    fun tag(tag: String, vararg attributes: Pair<String, Any>, content: (Generator.() -> Unit)? = null) {
        builder.apply {
            append("<$tag")
            for ((key, value) in attributes) {
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
        builder.append("<![CDATA[")
            .append(text)
            .append("]]>")
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

class WatchFaceScene(val fullwidth: Int, val fullheight: Int) : Generator() {
    fun partText(horizontal: Range = FULL, vertical: Range = FULL, text: WatchFaceText.() -> String) {
        WatchFaceText().apply {
            val height = (vertical.range * fullheight).roundToInt()
            tag(
                "PartText",
                "height" to height,
                "width" to (horizontal.range * fullwidth).roundToInt(),
                "x" to (horizontal.start * fullwidth).roundToInt(),
                "y" to (vertical.start * fullheight).roundToInt()
            ) {
                tag("Text") {
                    tag(
                        "Font",
                        "family" to "SYNC_TO_DEVICE",
                        "size" to height
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
        val complicationSlot = WatchFaceComplicationSlot((horizontal.range * fullwidth).roundToInt(), (vertical.range * fullheight).roundToInt()).apply {
            types()
        }

        tag(
            "ComplicationSlot",
            "slotId" to slotId,
            "supportedTypes" to complicationSlot.types.joinToString(" "),
            "height" to (vertical.range * fullheight).roundToInt(),
            "width" to (horizontal.range * fullwidth).roundToInt(),
            "x" to (horizontal.start * fullwidth).roundToInt(),
            "y" to (vertical.start * fullheight).roundToInt()
        ) {
            complicationSlot.build().let { builder.append(it) }
        }
    }

    fun partDraw(horizontal: Range = FULL, vertical: Range = FULL, alphaNormal: Double = 1.0, alphaAmbient: Double = 1.0, shapes: PartDraw.() -> Unit) {
        tag(
            "PartDraw",
            "height" to (vertical.range * fullheight).roundToInt(),
            "width" to (horizontal.range * fullwidth).roundToInt(),
            "x" to (horizontal.start * fullwidth).roundToInt(),
            "y" to (vertical.start * fullheight).roundToInt(),
            "alpha" to (alphaNormal * 255).roundToInt(),
        ) {
            tag(
                "Variant",
                "mode" to "AMBIENT",
                "target" to "alpha",
                "value" to (alphaAmbient * 255).roundToInt(),
            )
            PartDraw(
                fullwidth = (horizontal.range * fullwidth).roundToInt(),
                fullheight = (vertical.range * fullheight).roundToInt(),
            ).apply {
                shapes()
            }.build().let { builder.append(it) }
        }
    }
}

class PartDraw(val fullwidth: Int, val fullheight: Int) : Generator() {
    fun roundRectangle(horizontal: Range = FULL, vertical: Range = FULL, radiusX: Double = 0.5, radiusY: Double = 0.5, color: Color) {
        tag(
            "RoundRectangle",
            "height" to (vertical.range * fullheight).roundToInt(),
            "width" to (horizontal.range * fullwidth).roundToInt(),
            "x" to (horizontal.start * fullwidth).roundToInt(),
            "y" to (vertical.start * fullheight).roundToInt(),
            "cornerRadiusX" to (radiusX * horizontal.range * fullwidth).roundToInt(),
            "cornerRadiusY" to (radiusY * vertical.range * fullwidth).roundToInt(),
        ) {
            tag("Fill", "color" to color.run { "#%02x%02x%02x".format(red, green, blue) })
        }
    }
}

class WatchFaceComplicationSlot(val fullwidth: Int, val fullheight: Int) : Generator() {
    internal val types = mutableListOf("EMPTY")
    fun ofType(type: String, content: WatchFaceScene.() -> Unit) {
        types += type
        tag("Complication", "type" to type) {
            WatchFaceScene(fullwidth, fullheight)
                .apply { content() }
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
        roundRectangle(color = Color.BLACK)
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
