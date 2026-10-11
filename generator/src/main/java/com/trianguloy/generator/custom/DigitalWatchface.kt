package com.trianguloy.generator.wff.helpers

import com.trianguloy.generator.wff.Metadata.Companion.Metadata_ClockType
import com.trianguloy.generator.wff.Scene
import com.trianguloy.generator.wff.WatchFace

fun DigitalWatchface(initWatchFace: WatchFace.() -> Unit = {}, initScene: Scene.() -> Unit = { }) = WatchFace(450, 450) {
    +Metadata_ClockType()
    this.initWatchFace()
    +Scene(initScene)
}
