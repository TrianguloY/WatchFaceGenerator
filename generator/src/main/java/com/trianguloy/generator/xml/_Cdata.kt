package com.trianguloy.generator.xml

class _Cdata(val raw: String) : _Element {
    override fun toString() = "<![CDATA[$raw]]>"
}