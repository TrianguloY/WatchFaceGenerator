package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.xml.Cdata

class Template : Component("Template") {
    operator fun Cdata.unaryPlus() = super.add(this)

    operator fun Parameter.unaryPlus() = super.add(this)
}