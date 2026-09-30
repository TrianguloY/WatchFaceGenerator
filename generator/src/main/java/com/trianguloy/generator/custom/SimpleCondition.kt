package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Compare
import com.trianguloy.generator.wff.Condition
import com.trianguloy.generator.wff.Expression
import com.trianguloy.generator.wff.Expressions
import com.trianguloy.generator.xml.Cdata

fun SimpleCondition(expressions: SimpleCondition.() -> Unit) = Condition().apply {

    val simpleCondition = SimpleCondition()
    simpleCondition.expressions()

    +Expressions().apply {
        for (expression in simpleCondition.expressions) {
            +Expression(expression.name).apply {
                +Cdata(expression.check)
            }
        }
    }
    for (expression in simpleCondition.expressions) {
        +Compare(expression.name).apply {
            expression.then(this) // what black magic is this...
        }
    }
}

private var nextExpression = 0

class SimpleCondition internal constructor() {
    internal data class Expression(val name: String, val check: String, val then: Compare.() -> Unit)

    internal val expressions = mutableListOf<Expression>()

    fun generateIf(expression: String, name: String? = null, compare: Compare.() -> Unit) {
        expressions += Expression(name ?: "expression_${nextExpression++}", expression, compare)
    }
}