package com.trianguloy.definition

import com.trianguloy.generator.custom.horizontal
import com.trianguloy.generator.custom.ph
import com.trianguloy.generator.custom.pv
import com.trianguloy.generator.custom.vertical
import com.trianguloy.generator.wff.ComplicationType.SHORT_TEXT
import com.trianguloy.generator.wff.Image
import com.trianguloy.generator.wff.PartImage
import com.trianguloy.generator.wff.helpers.DigitalWatchface
import com.trianguloy.generator.wff.helpers.SimpleComplication
import com.trianguloy.generator.wff.helpers.SimpleText
import com.trianguloy.generator.xml.Comment

fun main() {
//    println("xml:")
//    xml()
    println("-".repeat(50))
    println("wff:")
    wff()
}

//fun xml() {
//    val tag = Tag("Parent").apply {
//        this["property1"] = 1
//        this["property2"] = 2
//        this + Tag("Children").apply {
//            this + Tag("Subchildren").apply {
//                this["abc"] = "def"
//            }
//        }
//    }
//
//    println(tag)
//}

fun wff() {
    val watchface = DigitalWatchface {
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
                    +PartImage().apply {
                        horizontal = 0 to height

                        +Image("[COMPLICATION.MONOCHROMATIC_IMAGE]")
                    }
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
                    +PartImage().apply {
                        horizontal = 0 to height

                        +Image("[COMPLICATION.MONOCHROMATIC_IMAGE]")
                    }
                    +SimpleText(partTextInit = { horizontal = height to width }) {
                        "${expression("[COMPLICATION.TEXT]")}%"
                    }
                }
            }
        }
    }

    println(watchface)
}