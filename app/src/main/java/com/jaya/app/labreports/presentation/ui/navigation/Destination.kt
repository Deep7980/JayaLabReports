package com.jaya.app.labreports.presentation.ui.navigation

import android.util.Log
import com.jaya.app.labreports.core.common.constants.AppRoutes
import java.util.UUID

sealed class Destination(
    protected val route: String,
    val id: String = UUID.randomUUID().toString(),
    val argumentKey: String? = null,
) {

    var fullRoute: String = if (argumentKey == null) route else {
        val builder = StringBuilder(route)
        builder.append("/{$argumentKey}")
        Log.e("FullRoute", builder.toString())
        builder.toString()
    }

    object NONE : Destination("")

    object Splash : Destination(route = AppRoutes.SPLASH, id = UUID.randomUUID().toString())

    object Login: Destination(route = AppRoutes.LOGIN, id = UUID.randomUUID().toString())

    object Dashboard: Destination(route = AppRoutes.DASHBOARD, id = UUID.randomUUID().toString())

    object AvailableQuote: Destination(route = AppRoutes.AVAILABLE_QUOTE, id = UUID.randomUUID().toString())

    object MyQuotes: Destination(route = AppRoutes.MY_QUOTES, id = UUID.randomUUID().toString())

    object Updates: Destination(route = AppRoutes.UPDATE, id = UUID.randomUUID().toString())

}