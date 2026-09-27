package com.trianguloy.generator.xml

internal class Cdata(val raw: String) : Element {
    override fun toString() = "<![CDATA[$raw]]>"
}