package com.jaya.app.labreports.presentation.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.Graph
import com.jaya.app.labreports.presentation.ui.screens.DashboardScreen
import com.jaya.app.labreports.presentation.ui.screens.LoginScreen
import com.jaya.app.labreports.presentation.ui.screens.SplashNavRoute
import com.jaya.app.labreports.presentation.ui.screens.UpdateScreen
import com.jaya.app.labreports.presentation.viewModels.MainViewModel

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    baseViewModel: MainViewModel
) {
    navigation(
        route = Graph.Main.fullRoute,
        startDestination = Destination.Splash.fullRoute,
    ) {
        SplashNavRoute.destination(navController, this)
        LoginScreen.destination(navController, this)
        DashboardScreen.destination(navController, this)
        UpdateScreen.destination(navController,this)

//        AvailableQuotationsScreen.destination(
//            navController = navController,
//            graphBuilder = this
//        )
    }
}