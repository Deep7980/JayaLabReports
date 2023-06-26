package com.jaya.app.labreports.presentation.ui.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NavHostController.AppGraphRoute(
    navChannel: Channel<NavGraphState>
){
    val currentActivity = LocalContext.current as? ComponentActivity
    LaunchedEffect(currentActivity, this, navChannel) {

        navChannel.receiveAsFlow().collect { navIntent ->
            currentActivity?.isFinishing?.let {
                if (it) return@collect
            }

            when (navIntent) {
                is NavGraphState.NavigateTo -> {
                    navIntent.graph?.fullRoute?.let {
                        navigate(navIntent.graph.fullRoute) {
                            launchSingleTop = navIntent.singleTop
                            navIntent.popTo?.let {
                                popUpTo(it.fullRoute) {
                                    inclusive = navIntent.inclusive
                                }
                            }
                        }
                    }
                }
                is NavGraphState.NavigateBack -> {
                    if(navIntent.graph != null) {
                        popBackStack(navIntent.graph.fullRoute, navIntent.inclusive)
                    } else {
                        popBackStack()
                    }
                }
            }
        }
    }

}