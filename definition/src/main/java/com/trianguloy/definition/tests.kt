package com.trianguloy.definition

import com.trianguloy.generator.custom.horizontal
import com.trianguloy.generator.custom.ph
import com.trianguloy.generator.custom.pv
import com.trianguloy.generator.custom.vertical
import com.trianguloy.generator.wff.BoundingBox
import com.trianguloy.generator.wff.Complication
import com.trianguloy.generator.wff.ComplicationSlot
import com.trianguloy.generator.wff.ComplicationType.SHORT_TEXT
import com.trianguloy.generator.wff.Font
import com.trianguloy.generator.wff.Image
import com.trianguloy.generator.wff.Metadata
import com.trianguloy.generator.wff.Metadata.ClockTypeValues.DIGITAL
import com.trianguloy.generator.wff.Parameter
import com.trianguloy.generator.wff.PartImage
import com.trianguloy.generator.wff.PartText
import com.trianguloy.generator.wff.Scene
import com.trianguloy.generator.wff.Template
import com.trianguloy.generator.wff.Text
import com.trianguloy.generator.wff.WatchFace
import com.trianguloy.generator.xml.Cdata
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
    val watchface = WatchFace(450, 450).apply {
        +Metadata.ClockType(DIGITAL)
        +Scene().apply {
            +Comment("Date 1")
            +PartText().apply {
                vertical = pv(0.1) to pv(0.15)
                +Text().apply {
                    +Font(height).apply {
                        +Template().apply {
                            +Cdata("%s/%s/%s")
                            +Parameter("[YEAR]")
                            +Parameter("[MONTH_Z]")
                            +Parameter("[DAY]")
                        }
                    }
                }
            }

            +Comment("Date 2")
            +PartText().apply {
                vertical = pv(0.15) to pv(0.2)
                +Text().apply {
                    +Font(height).apply {
                        +Template().apply {
                            +Cdata("%s - %s")
                            +Parameter("[MONTH_F]")
                            +Parameter("[DAY_OF_WEEK_F]")
                        }
                    }
                }
            }

            +Comment("Watch battery")
            +ComplicationSlot().apply {
                slotId = "watch_battery"
                name = "Watch battery"
                supportedTypes += SHORT_TEXT
                horizontal = ph(0.12) to ph(0.31)
                vertical = pv(0.21) to pv(0.26)

                +BoundingBox()
                +Complication(SHORT_TEXT).apply {
                    +PartImage().apply {
                        horizontal = 0 to height

                        +Image("[COMPLICATION.MONOCHROMATIC_IMAGE]")
                    }
                    +PartText().apply {
                        horizontal = height to width

                        +Text().apply {
                            +Font(size = height).apply {
                                +Template().apply {
                                    +Cdata("%s%%")
                                    +Parameter("[COMPLICATION.TEXT]")
                                }
                            }
                        }
                    }
                }
            }

            +Comment("Phone battery")
            +ComplicationSlot().apply {
                slotId = "phone_battery"
                name = "Phone battery"
                supportedTypes += SHORT_TEXT
                horizontal = ph(0.31) to ph(0.5)
                vertical = pv(0.21) to pv(0.26)

                +BoundingBox()
                +Complication(SHORT_TEXT).apply {
                    +PartImage().apply {
                        horizontal = 0 to height
                        +Image("[COMPLICATION.MONOCHROMATIC_IMAGE]")
                    }
                    +PartText().apply {
                        horizontal = height to width
                        +Text().apply {
                            +Font(size = height).apply {
                                +Template().apply {
                                    +Cdata("%s%%")
                                    +Parameter("[COMPLICATION.TEXT]")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    println(watchface)
}