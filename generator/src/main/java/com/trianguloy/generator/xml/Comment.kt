package com.trianguloy.generator.xml


class Comment(val message: String) : Element {
    override fun toString() = "<!-- $message -->"
}