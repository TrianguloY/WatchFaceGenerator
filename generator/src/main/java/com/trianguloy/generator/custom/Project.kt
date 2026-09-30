package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.WatchFace
import java.io.File


fun WatchFace.toProject() {
    File("watchface/src/main/res/raw", "watchface.xml")
        .writeText(this.toString())
}