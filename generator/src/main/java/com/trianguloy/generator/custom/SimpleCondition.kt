package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Compare
import com.trianguloy.generator.wff.Condition
import com.trianguloy.generator.wff.Default
import com.trianguloy.generator.wff.Expression
import com.trianguloy.generator.wff.Expressions
import com.trianguloy.generator.wff.helpers.Componentt
import com.trianguloy.generator.xml.Cdata

fun Componentt.SimpleCondition(expressions: SimpleCondition.() -> Unit) = Condition {

    val simpleCondition = SimpleCondition(this)
    simpleCondition.expressions()

    +Expressions {
        for ((name, check) in simpleCondition.expressions) {
            +Expression(name) {
                +Cdata(check)
            }
        }
    }
    for (expression in simpleCondition.expressions) {
        +Compare(expression.name) {
            expression.then(this)
        }
    }
    simpleCondition.default?.let { +it }
}

private var nextExpression = 0

class SimpleCondition internal constructor(private val condition: Condition) {
    internal data class Expression(val name: String, val check: String, val then: Compare.() -> Unit = {})

    internal val expressions = mutableListOf<Expression>()
    internal var default: Default? = null

    fun generateIf(expression: String, name: String? = null, compare: Compare.() -> Unit = {}) {
        expressions += Expression(name ?: "expression_${nextExpression++}", expression, compare)
    }

    fun elseGenerate(scene: Default.() -> Unit = {}) {
        default = condition.Default { scene() }
    }
}