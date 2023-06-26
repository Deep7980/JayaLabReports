package com.jaya.app.labreports.core.model

import com.jaya.app.labreports.core.common.enums.Page

data class Navigation(
    val destination: Page,
    val popDestination: Page = Page.NONE,
    val popUpto: Boolean = false,
    val singleTop: Boolean = true
)
