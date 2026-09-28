package com.trianguloy.generator.wff.helpers

import com.trianguloy.generator.wff.Metadata
import com.trianguloy.generator.wff.Metadata.ClockTypeValues.DIGITAL
import com.trianguloy.generator.wff.Scene
import com.trianguloy.generator.wff.WatchFace

fun DigitalWatchface(initWatchFace: WatchFace.() -> Unit = {}, scene: WatchFace.() -> Scene.() -> Unit) = WatchFace(450, 450).apply {
    +Metadata.ClockType(DIGITAL)
    this.initWatchFace()
    +Scene().apply {
        scene()() // this is ugly
    }
}
