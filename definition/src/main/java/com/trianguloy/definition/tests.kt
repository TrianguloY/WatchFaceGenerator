package com.trianguloy.definition

import com.trianguloy.generator.wff.Font
import com.trianguloy.generator.wff.Metadata
import com.trianguloy.generator.wff.Metadata.ClockTypeValues.DIGITAL
import com.trianguloy.generator.wff.Parameter
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
                vertical = 0.1.sh to 0.15.sh
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
        }
    }

    println(watchface)
}