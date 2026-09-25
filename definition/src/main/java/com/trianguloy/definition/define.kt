package com.trianguloy.definition


import com.trianguloy.generator.WatchFace
import java.awt.Color


///////////// Design ///////////////
fun main() = WatchFace(height = 450, width = 450) {
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

    comment("Watch Battery")
    complication(
        id = "watch_battery",
        name = "Watch battery",
        horizontal = 0.12..0.31,
        vertical = 0.21..0.26
    ) {
        ofType("SHORT_TEXT") {
            partImage(horizontal = 0.0..0.3, image = "[COMPLICATION.MONOCHROMATIC_IMAGE]")
            partText(horizontal = 0.3..1.0) {
                "${expression("[COMPLICATION.TEXT]")}%"
            }
        }
    }

    comment("Phone Battery")
    complication(
        id = "phone_battery",
        name = "Phone battery",
        horizontal = 0.31..0.5,
        vertical = 0.21..0.26,
    ) {
        ofType("SHORT_TEXT") {
            partImage(horizontal = 0.0..0.3, image = "[COMPLICATION.MONOCHROMATIC_IMAGE]")
            partText(horizontal = 0.3..1.0) {
                "${expression("[COMPLICATION.TEXT]")}%"
            }
        }
    }

    comment("Top shortcut")
    complication(
        id = "top_shortcut",
        name = "Top shortcut",
        horizontal = 0.1..0.5,
        vertical = 0.3..0.5,
    ) {
        ofType("SHORT_TEXT") {
            partImage(vertical = 0.2..0.8, horizontal = 0.0..0.3, image = "[COMPLICATION.MONOCHROMATIC_IMAGE]")
            partText(vertical = 0.3..0.7, horizontal = 0.3..1.0) {
                expression("[COMPLICATION.TEXT]")
            }
        }
    }

    comment("Bottom shortcut")
    complication(
        id = "bottom_shortcut",
        name = "Bottom shortcut",
        horizontal = 0.1..0.5,
        vertical = 0.5..0.7,
    ) {
        ofType("SHORT_TEXT") {
            partImage(vertical = 0.2..0.8, horizontal = 0.0..0.3, image = "[COMPLICATION.MONOCHROMATIC_IMAGE]")
            partText(vertical = 0.3..0.7, horizontal = 0.3..1.0) {
                expression("[COMPLICATION.TEXT]")
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
            expression("[UNREAD_NOTIFICATION_COUNT] > 0") {
                partDraw {
                    roundRectangle(Color.WHITE)
                }
                partText(color = Color.BLACK) {
                    expression("[UNREAD_NOTIFICATION_COUNT]")
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