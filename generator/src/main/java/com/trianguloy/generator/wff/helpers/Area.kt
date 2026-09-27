package com.trianguloy.generator.wff.helpers

open class Size internal constructor(name: String, width: Int, height: Int) : Component(name) {
    constructor(name: String, parent: Size) : this(name, parent.width, parent.height)

    var height by delegate(height)
    var width by delegate(width)
}

open class Area internal constructor(name: String, width: Int, height: Int) : Size(name, width, height) {

    constructor(name: String, parent: Size) : this(name, parent.width, parent.height)

    var x by delegate(0)
    var y by delegate(0)
}