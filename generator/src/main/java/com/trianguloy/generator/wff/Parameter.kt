package com.trianguloy.generator.wff

import com.trianguloy.generator.wff.helpers.Component

class Parameter(expression: String) : Component("Parameter") {
    var expression by delegate(expression)

}