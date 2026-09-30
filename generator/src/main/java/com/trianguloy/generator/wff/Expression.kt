package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component
import com.trianguloy.generator.xml.Cdata

class Expression(name: String) : Component("Expression") {
    var name by delegate(name)

    operator fun Cdata.unaryPlus() = super.add(this)
}