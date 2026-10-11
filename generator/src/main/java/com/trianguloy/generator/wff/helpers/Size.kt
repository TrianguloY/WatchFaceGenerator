package com.trianguloy.generator.wff.helpers

interface Size {
    var width: Int
    var height: Int
}

class SizeImpl internal constructor(component: Component, width: Int? = null, height: Int? = null) : Size {
    override var width by component.delegate(width ?: findWidth(component.parent))

    override var height by component.delegate(height ?: findHeight(component.parent))

}

internal fun findWidth(component: Componentt?) = findProperty(component) { if (this is Size) this.width else null }
internal fun findHeight(component: Componentt?) = findProperty(component) { if (this is Size) this.height else null }

private fun <T> findProperty(component: Componentt?, getter: Componentt.() -> T?): T {
    if (component == null) throw Exception("Unable to get property")
    return getter(component) ?: findProperty(component.parent, getter)
}
