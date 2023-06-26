package com.jaya.app.labreports.presentation.ui.navigation

import android.util.Log
import com.jaya.app.labreports.core.common.constants.AppGraphs
import java.util.UUID

sealed class Graph(
    protected val route: String,
    val id: String = UUID.randomUUID().toString(),
    argumentKey: String? = null,
) {
    val fullRoute: String = if (argumentKey == null) route else {
        val builder = StringBuilder(route)
        builder.append("/{$argumentKey}")
        Log.e("FullRoute", builder.toString())
        builder.toString()
    }

    object None: Graph(route = "")

    object Main: Graph(route = AppGraphs.MAIN)

    object Dashboard: Graph(route = AppGraphs.DASHBOARD)
}