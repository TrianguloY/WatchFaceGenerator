package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import java.awt.Color

class Fill : Component("Fill") {
    var color by delegate(Color.WHITE)
}