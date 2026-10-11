package com.trianguloy.definition

import com.trianguloy.generator.wff.Metadata.Companion.Metadata_ClockType
import com.trianguloy.generator.wff.PartText
import com.trianguloy.generator.wff.Scene
import com.trianguloy.generator.wff.WatchFace
import com.trianguloy.generator.xml.Comment
import com.trianguloy.generator.xml.Tag

fun main() {
    println("xml:")
    xml()
    println("-".repeat(50))
    println("wff:")
    wff()
}

fun xml() {
    val tag = Tag("Parent") {
        this["property1"] = 1
        this["property2"] = 2
        +Tag("Children") {
            +Tag("Subchildren") {
                this["abc"] = "def"
            }
        }
    }

    println(tag)
}

fun wff() {
    val watchface = WatchFace(450, 450) {
        +Comment("Date 1")
        +Metadata_ClockType {}
        +Scene {
            +PartText {

            }
        }
    }

    println(watchface)
}