package com.trianguloy.generator.xml


class _Comment(val message: String) : _Element {
    override fun toString() = "<!-- $message -->"
}