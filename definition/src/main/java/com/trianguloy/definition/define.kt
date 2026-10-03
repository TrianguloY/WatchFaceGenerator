package com.trianguloy.definition

import com.trianguloy.generator.custom.Elipse
import com.trianguloy.generator.custom.Rectangle
import com.trianguloy.generator.custom.SimpleCondition
import com.trianguloy.generator.custom.SimpleDraw
import com.trianguloy.generator.custom.SimpleGroup
import com.trianguloy.generator.custom.SimpleImage
import com.trianguloy.generator.custom.SimpleText
import com.trianguloy.generator.custom.SimpleTimeText
import com.trianguloy.generator.custom.ambient
import com.trianguloy.generator.custom.horizontal
import com.trianguloy.generator.custom.ph
import com.trianguloy.generator.custom.pv
import com.trianguloy.generator.custom.toProject
import com.trianguloy.generator.custom.vertical
import com.trianguloy.generator.wff.ComplicationType.SHORT_TEXT
import com.trianguloy.generator.wff.TimeText.Format.HOUR
import com.trianguloy.generator.wff.TimeText.Format.MINUTES
import com.trianguloy.generator.wff.helpers.DigitalWatchface
import com.trianguloy.generator.wff.helpers.SimpleComplication
import com.trianguloy.generator.xml.Comment
import java.awt.Color.BLACK
import java.awt.Color.WHITE

fun main() {
    DigitalWatchface {
        {
            +Comment("Date 1")
            +SimpleText(partTextInit = { vertical = pv(0.1) to pv(0.15) }) {
                "${expression("[YEAR]")}/${expression("[MONTH_Z]")}/${expression("[DAY]")}"
            }

            +Comment("Date 2")
            +SimpleText(partTextInit = { vertical = pv(0.15) to pv(0.2) }) {
                "${expression("[MONTH_F]")} - ${expression("[DAY_OF_WEEK_F]")}"
            }

            +Comment("Watch battery")
            +SimpleComplication(complicationSlotInit = {
                slotId = "watch_battery"
                name = "Watch battery"
                horizontal = ph(0.12) to ph(0.31)
                vertical = pv(0.21) to pv(0.26)
            }) {
                forType(SHORT_TEXT) {
                    +SimpleImage({ horizontal = 0 to height }, "[COMPLICATION.MONOCHROMATIC_IMAGE]")
                    +SimpleText(partTextInit = { horizontal = height to width }) {
                        "${expression("[COMPLICATION.TEXT]")}%"
                    }
                }
            }

            +Comment("Phone battery")
            +SimpleComplication(complicationSlotInit = {
                slotId = "phone_battery"
                name = "Phone battery"
                horizontal = ph(0.31) to ph(0.5)
                vertical = pv(0.21) to pv(0.26)
            }) {
                forType(SHORT_TEXT) {
                    +SimpleImage({ horizontal = 0 to height }, "[COMPLICATION.MONOCHROMATIC_IMAGE]")
                    +SimpleText(partTextInit = { horizontal = height to width }) {
                        "${expression("[COMPLICATION.TEXT]")}%"
                    }
                }
            }

            +Comment("Top shortcut")
            +SimpleComplication({
                slotId = "top_shortcut"
                name = "Top shortcut"
                horizontal = ph(0.1) to ph(0.5)
                vertical = pv(0.3) to pv(0.5)
            }) {
                forType(SHORT_TEXT) {
                    +SimpleImage({ vertical = pv(0.2) to pv(0.8); horizontal = ph(0.0) to ph(0.3) }, "[COMPLICATION.MONOCHROMATIC_IMAGE]")
                    +SimpleText({ vertical = pv(0.3) to pv(0.7); horizontal = ph(0.3) to ph(1.0) }) {
                        expression("[COMPLICATION.TEXT]")
                    }
                }
            }

            +Comment("Bottom shortcut")
            +SimpleComplication({
                slotId = "bottom_shortcut"
                name = "Bottom shortcut"
                horizontal = ph(0.1) to ph(0.5)
                vertical = pv(0.5) to pv(0.7)
            }) {
                forType(SHORT_TEXT) {
                    +SimpleImage({ vertical = pv(0.2) to pv(0.8); horizontal = ph(0.0) to ph(0.3) }, "[COMPLICATION.MONOCHROMATIC_IMAGE]")
                    +SimpleText({ vertical = pv(0.3) to pv(0.7); horizontal = ph(0.3) to ph(1.0) }) {
                        expression("[COMPLICATION.TEXT]")
                    }
                }
            }

            +Comment("AOD")
            +SimpleDraw {
                alpha = 0
                ambient(::alpha, 128)

                +Elipse { color = BLACK }
            }

            +Comment("Ambient indicator")
            +SimpleDraw {
                ambient(::alpha, 0)
                horizontal = ph(1.0) - 4 to ph(1.0)
                vertical = pv(0.5) - 2 to pv(0.5) + 2
                +Rectangle { }
            }

            +Comment("Notification counter")
            +SimpleCondition {
                generateIf("[UNREAD_NOTIFICATION_COUNT] > 0") {
                    +SimpleGroup {
                        horizontal = ph(0.9) to ph(0.98)
                        vertical = pv(0.46) to pv(0.54)
                        +SimpleDraw {
                            +Elipse {
                                color = WHITE
                            }
                        }
                        +SimpleText(fontInit = { color = BLACK }) {
                            expression("[UNREAD_NOTIFICATION_COUNT]")
                        }
                    }
                }
            }

            +Comment("Hours")
            +SimpleTimeText({
                horizontal = ph(0.5) to ph(0.9)
                vertical = pv(0.17) to pv(0.54)
            }, HOUR)
            +Comment("Minutes")
            +SimpleTimeText({
                horizontal = ph(0.5) to ph(0.9)
                vertical = pv(0.46) to pv(0.84)
            }, MINUTES)
        }
    }.toProject()

}
