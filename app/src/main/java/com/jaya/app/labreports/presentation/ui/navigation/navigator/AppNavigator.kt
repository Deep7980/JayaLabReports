package com.jaya.app.labreports.presentation.ui.navigation.navigator

import android.util.Log
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.NavArgumentListener
import com.jaya.app.labreports.presentation.ui.navigation.NavigationState
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppNavigator : RouteNavigator {
    override fun onNavigated(state: NavigationState) {
        val result = navigationState.compareAndSet(state, NavigationState.Idle)
        Log.d("TESTING", "onNavigated: $result")
        clearNavListener()
    }

    override fun popToRoute(destination: Destination, inclusive: Boolean) =
        navigateBack(NavigationState.Pop(destination, inclusive))

    override fun navigateToRoute(
        destination: Destination,
        popToRoute: Destination,
        inclusive: Boolean,
        singleTop: Boolean,
    ) = navigateTo(
        NavigationState.NavigateToRoute(
            destination = destination,
            popToRoute = popToRoute,
            inclusive = inclusive,
            isSingleTop = singleTop,
        )
    )

    override fun tryNavigateTo(
        route: String,
        popUpToRoute: String?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {

    }

    override fun popAndNavigate(
        destination: Destination,
        singleTop: Boolean,
        inclusive: Boolean
    ) = navigateTo(
        NavigationState.PopAndNavigate(
            destination = destination,
            singleTop = singleTop,
            inclusive = inclusive
        )
    )

    override fun listenNavArgument(listener: NavArgumentListener) {
        if (argumentListener != null) return
        argumentListener = listener
    }

    override val navigationState: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.Idle)

    override var argumentListener: NavArgumentListener? = null

    private fun navigateTo(state: NavigationState) {
        navigationState.tryEmit(state)
    }

    private fun navigateBack(state: NavigationState) {
        navigationState.tryEmit(state)
    }


}