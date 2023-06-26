package com.jaya.app.labreports.presentation.ui.navigation

sealed class NavigationState {

    object Idle : NavigationState()

    data class NavigateToRoute(
        val destination: Destination,
        val popToRoute: Destination = Destination.NONE,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationState()

    data class Pop(
        val destination: Destination = Destination.NONE,
        val inclusive: Boolean = false,
    ) : NavigationState()

    data class PopAndNavigate(
        val destination: Destination = Destination.NONE,
        val singleTop: Boolean = false,
        val inclusive: Boolean = false,
    ): NavigationState()
}