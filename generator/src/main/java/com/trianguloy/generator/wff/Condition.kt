package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Condition : Component("Condition") {

    operator fun Expressions.unaryPlus() = super.add(this)
    operator fun Compare.unaryPlus() = super.add(this)
    operator fun Default.unaryPlus() = super.add(this)
}