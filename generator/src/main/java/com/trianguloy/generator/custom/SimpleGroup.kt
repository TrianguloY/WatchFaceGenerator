package com.trianguloy.generator.custom

import com.trianguloy.generator.wff.Group
import com.trianguloy.generator.wff.helpers.Size

// remove if dsl constructors are added to the wff themselves
fun Size.SimpleGroup(groupInit: Group.() -> Unit) = Group().apply { groupInit() }