package com.trianguloy.generator.wff.helpers

open class Size internal constructor(name: String, internal val parentWidth: Int, internal val parentHeight: Int) : Component(name) {
    constructor(name: String, parent: Size) : this(name, parent.width, parent.height)

    var height by delegate(parentHeight)
    var width by delegate(parentWidth)
}

open class Area internal constructor(name: String, width: Int, height: Int) : Size(name, width, height) {

    constructor(name: String, parent: Size) : this(name, parent.width, parent.height)

    var x by delegate(0)
    var y by delegate(0)


}