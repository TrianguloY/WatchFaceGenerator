package com.trianguloy.generator.wff.helpers

interface Area : Size {
    var x: Int
    var y: Int
}

class AreaImpl internal constructor(component: Component) : Area, Size by SizeImpl(component) {
    override var x by component.delegate(0)
    override var y by component.delegate(0)
}