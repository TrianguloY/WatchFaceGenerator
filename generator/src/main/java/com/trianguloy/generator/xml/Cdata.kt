package com.trianguloy.generator.xml

class Cdata(val raw: String) : Element {
    override fun toString() = "<![CDATA[$raw]]>"
}