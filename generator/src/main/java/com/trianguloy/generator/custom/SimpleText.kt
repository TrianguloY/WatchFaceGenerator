package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Font
import com.trianguloy.generator.wff.Parameter
import com.trianguloy.generator.wff.PartText
import com.trianguloy.generator.wff.Template
import com.trianguloy.generator.wff.Text
import com.trianguloy.generator.wff.helpers.Size
import com.trianguloy.generator.xml.Cdata

fun Size.SimpleText(
    partTextInit: PartText.() -> Unit = {},
    textInit: Text.() -> Unit = {},
    fontInit: Font.() -> Unit = {},
    templateInit: Template.() -> Unit = {},
    text: SimpleText .() -> String
) = PartText().apply {
    partTextInit()
    +Text().apply {
        textInit()
        +Font(height).apply {
            fontInit()
            +Template().apply {
                templateInit()

                val simpleText = SimpleText()

                +Cdata(simpleText.text().replace("%", "%%").replace(REPLACEMENT_VALUE_DO_NOT_USE, "%s"))
                simpleText.expressions.forEach {
                    +Parameter(it)
                }

                simpleText.lines?.let {
                    maxLines = it
                    size = parentHeight / it
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