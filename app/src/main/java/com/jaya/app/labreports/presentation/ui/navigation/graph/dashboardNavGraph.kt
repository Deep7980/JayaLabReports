package com.jaya.app.labreports.presentation.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.Graph
import com.jaya.app.labreports.presentation.ui.screens.DashboardScreen
import com.jaya.app.labreports.presentation.ui.screens.LoginScreen
import com.jaya.app.labreports.presentation.ui.screens.MyQuotesScreen
import com.jaya.app.labreports.utilities.parent_child_relationship.ParentViewModel

fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
    parentViewModel: ParentViewModel? = null
) {
    navigation(
        route = Graph.Dashboard.fullRoute,
        startDestination = Destination.MyQuotes.fullRoute,
    ) {
        //DashboardScreen.destination(navController, this)
        MyQuotesScreen.destination(
            navController = navController,
            graphBuilder = this,
            parentViewModel = parentViewModel
        )
//        OrdersScreen.destination(
//            navController = navController,
//            graphBuilder = this,
//            parentViewModel = parentViewModel
//        )
//        CompletedScreen.destination(
//            navController = navController,
//            graphBuilder = this,
//            parentViewModel = parentViewModel
//        )
    }
}