package com.trianguloy.definition

import com.trianguloy.generator.wff.Metadata
import com.trianguloy.generator.wff.Metadata.ClockTypeValues.DIGITAL
import com.trianguloy.generator.wff.PartText
import com.trianguloy.generator.wff.Scene
import com.trianguloy.generator.wff.WatchFace
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
    val watchface = WatchFace(100, 100).apply {
        this + Comment("A comment")
        this + Metadata.ClockType(DIGITAL)
        this + Scene().apply {
            this + PartText()
        }
    }

    println(watchface)
}