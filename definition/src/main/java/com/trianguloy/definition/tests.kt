package com.trianguloy.definition

import com.trianguloy.generator.wff.Metadata
import com.trianguloy.generator.wff.Scene
import com.trianguloy.generator.wff.WatchFace
import com.trianguloy.generator.xml._Comment
import com.trianguloy.generator.xml._Tag

fun main() {
    println("xml:")
    xml()
    println("-".repeat(50))
    println("wff:")
    wff()
}

fun xml() {
    val tag = _Tag("Parent").apply {
        this["property1"] = 1
        this["property2"] = 2
        this + _Tag("Children").apply {
            this + _Tag("Subchildren").apply {
                this["abc"] = "def"
            }
        }
    }

    println(tag)
}

fun wff() {
    val watchface = WatchFace(100, 100).apply {
        this + _Comment("A comment")
        this + Metadata.ClockType(Metadata.ClockTypeValues.DIGITAL)
        this + Scene().apply {
            this + _Comment("To be defined")
        }
    }

    println(watchface)
}