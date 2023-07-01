package com.jaya.app.labreports.presentation.ui.navigation

import kotlinx.coroutines.flow.StateFlow

interface RouteNavigator {

    fun onNavigated(state: NavigationState)
    fun popToRoute(destination: Destination, inclusive: Boolean)
    fun navigateToRoute(
        destination: Destination,
        popToRoute: Destination,
        inclusive: Boolean,
        singleTop: Boolean,
    )
    fun tryNavigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )
    fun popAndNavigate(destination: Destination, singleTop: Boolean, inclusive: Boolean)

    fun listenNavArgument(listener: NavArgumentListener)

    fun clearNavListener() { argumentListener = null }

    val navigationState: StateFlow<NavigationState>

    var argumentListener: NavArgumentListener?
}