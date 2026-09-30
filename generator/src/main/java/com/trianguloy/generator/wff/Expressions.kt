package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Expressions : Component("Expressions") {


    operator fun Expression.unaryPlus() = super.add(this)
}