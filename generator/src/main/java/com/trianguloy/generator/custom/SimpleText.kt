package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Font
import com.trianguloy.generator.wff.Parameter
import com.trianguloy.generator.wff.PartText
import com.trianguloy.generator.wff.Template
import com.trianguloy.generator.wff.Text
import com.trianguloy.generator.wff.helpers.Componentt
import com.trianguloy.generator.xml.Cdata

fun Componentt.SimpleText(
    partTextInit: PartText.() -> Unit = {},
    textInit: Text.() -> Unit = {},
    fontInit: Font.() -> Unit = {},
    templateInit: Template.() -> Unit = {},
    text: SimpleText .() -> String
) = PartText {
    partTextInit()
    +Text {
        textInit()
        +Font(this@PartText.height) {
            fontInit()
            +Template {
                templateInit()

                val simpleText = SimpleText()

                +Cdata(simpleText.text().replace("%", "%%").replace(REPLACEMENT_VALUE_DO_NOT_USE, "%s"))
                simpleText.expressions.forEach {
                    +Parameter(it)
                }

                simpleText.lines?.let {
                    this@Text.maxLines = it
                    this@Font.size = (this@PartText.height * 0.8 / it).toInt()
                }
            }
        }
    }
}

internal const val REPLACEMENT_VALUE_DO_NOT_USE = "$!@#@!$"

class SimpleText internal constructor() {
    internal val expressions = mutableListOf<String>()

    var lines: Int? = null

    fun expression(expression: String) = REPLACEMENT_VALUE_DO_NOT_USE.also {
        expressions.add(expression)
    }
}