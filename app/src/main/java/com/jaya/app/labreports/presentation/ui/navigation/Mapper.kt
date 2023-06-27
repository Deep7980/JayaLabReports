package com.jaya.app.labreports.presentation.ui.navigation

import com.jaya.app.labreports.core.common.enums.Page

fun Page.toDestination(): Destination = when (this) {
    Page.LOGIN -> Destination.Login
    Page.SPLASH -> Destination.Splash
    Page.DASHBOARD -> Destination.Dashboard
//    Page.NONE -> Destination.NONE
//    Page.COMPLETED -> Destination.Completed
    Page.MYQUOTE -> Destination.MyQuotes
//    Page.ORDERS -> Destination.Orders
//    Page.AVAILABLE_QUOTES -> Destination.AvailableQuote

    else -> Destination.NONE
}

fun Destination.toPage(): Page = when (this) {
//    Destination.Completed -> COMPLETED
    Destination.Dashboard -> Page.DASHBOARD
    Destination.Login -> Page.LOGIN
//    Destination.AvailableQuote -> AVAILABLE_QUOTES
//    Destination.NONE -> NONE
//    Destination.Orders -> Page.ORDERS
    Destination.Splash -> Page.SPLASH
    Destination.MyQuotes -> Page.MYQUOTE
    else -> Page.NONE
}