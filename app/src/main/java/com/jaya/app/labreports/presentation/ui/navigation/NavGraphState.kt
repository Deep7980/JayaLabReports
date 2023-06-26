package com.jaya.app.labreports.presentation.ui.navigation

sealed class NavGraphState{

    data class NavigateTo(
        val graph: Graph? = null,
        val popTo: Graph? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false,
    ) : NavGraphState()

    data class NavigateBack(
        val graph: Graph? = null,
        val inclusive: Boolean = false,
    ) : NavGraphState()
}
