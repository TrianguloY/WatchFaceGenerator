@file:Import("generator.kts")

import java.awt.Color
import java.io.File


///////////// Design ///////////////

print("Generating")

val content = WatchFace(height = 450, width = 450)
{
    comment("Date 1")
    partText(
        vertical = 0.1..0.15,
    ) {
        "${expression("[YEAR]")}/${expression("[MONTH_Z]")}/${expression("[DAY]")}"
    }

    comment("Date 2")
    partText(
        vertical = 0.15..0.2
    ) {
        "${expression("[MONTH_F]")} - ${expression("[DAY_OF_WEEK_F]")}"
    }

    comment("Watch battery")
    partText(
        horizontal = 0.12..0.31,
        vertical = 0.21..0.26
    ) {
        "⌚${expression("[BATTERY_PERCENT]")}%"
    }

    comment("Phone Battery")
    complication(
        id = "1",
        horizontal = 0.31..0.5,
        vertical = 0.21..0.26,
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
        vertical = 0.17..0.84
    ) {
        comment("Hour")
        timeText(vertical = 0.0..0.55) { "hh" }

        comment("Minutes")
        timeText(vertical = 0.45..1.0) { "mm" }
    }

}

File("../watchface/src/main/res/raw", "watchface.xml").writeText(content)
print("Generated")
