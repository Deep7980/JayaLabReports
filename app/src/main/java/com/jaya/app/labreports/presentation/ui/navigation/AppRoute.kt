package com.jaya.app.labreports.presentation.ui.navigation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.jaya.app.labreports.R
import com.jaya.app.labreports.presentation.ui.navigation.screen_transitions.enterTransition
import com.jaya.app.labreports.presentation.ui.navigation.screen_transitions.exitTransition
import com.jaya.app.labreports.presentation.ui.navigation.screen_transitions.popEnterTransition
import com.jaya.app.labreports.presentation.ui.navigation.screen_transitions.popExitTransition
import com.jaya.app.labreports.utilities.Text
import com.jaya.app.labreports.utilities.parent_child_relationship.ChildViewModel
import com.jaya.app.labreports.utilities.parent_child_relationship.ParentViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AppRoute<T : RouteNavigator> {
    val destination: Destination

    @Composable
    fun attachedVM(): T

    fun networkConnectivity(viewModel: T): StateFlow<Boolean> = MutableStateFlow(true)

    fun routeArguments(): List<NamedNavArgument> = emptyList()

    fun deepLinks(): List<NavDeepLink> = emptyList()

    @Composable
    fun Content(viewModel: T)


    @Composable
    fun AlternativeContent(viewModel: T): Unit = Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        R.string.no_internet.Text(style = MaterialTheme.typography.h1)
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun destination(
        navController: NavHostController,
        graphBuilder: NavGraphBuilder,
        parentViewModel: ParentViewModel? = null
    ) {
        graphBuilder.composable(route = destination.fullRoute,
            arguments = routeArguments(),
            deepLinks = deepLinks(),
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }) {
            val arguments = remember { it.arguments?.getString(destination.argumentKey) }
            val viewModel = attachedVM()
            val currentActivity = LocalContext.current as? ComponentActivity

            Crossfade(
                targetState = networkConnectivity(viewModel).collectAsState().value,
                animationSpec = tween(
                    durationMillis = 300
                ),
            ) { state ->
                Log.d("ConnectivityState", "$state")
                if (state) Content(viewModel = viewModel)
                else AlternativeContent(viewModel = viewModel)
            }

            LaunchedEffect(key1 = Unit) {
                arguments?.let {
                    viewModel.argumentListener?.onArgumentReceived(arguments)
                }
                parentViewModel?.let { parent ->
                    if (viewModel is ChildViewModel) {
                        parent.parentOrders.let { orders ->
                            viewModel.listenParentOrders(orders)
                        }
                        viewModel.childRequestFlow.let {
                            parent.listenChildRequests(it)
                        }
                    }
                }
            }

            navController.NavEffects(
                activity = currentActivity,
                navState = viewModel.navigationState,
                onNavigated = {
                    parentViewModel?.onClear()
                    if (viewModel is ChildViewModel) {
                        viewModel.onClear()
                    }
                    viewModel.onNavigated(it)
                })
        }
    }

    @Composable
    private fun NavHostController.NavEffects(
        activity: ComponentActivity?,
        navState: StateFlow<NavigationState>,
        onNavigated: (navState: NavigationState) -> Unit,
    ) {
        LaunchedEffect(activity, navState) {
            navState.collect { state ->
                activity?.isFinishing?.let {
                    if (it) return@collect
                }

                when (state) {
                    is NavigationState.NavigateToRoute -> {
                        navigate(state.destination.fullRoute) {
                            launchSingleTop = state.isSingleTop
                            if (state.popToRoute != Destination.NONE) {
                                popUpTo(state.popToRoute.fullRoute) {
                                    inclusive = state.inclusive
                                }
                            }
                        }
                    }

                    is NavigationState.Pop -> {
                        if (state.destination != Destination.NONE) {
                            popBackStack(
                                route = state.destination.fullRoute, inclusive = state.inclusive
                            )
                        } else {
                            popBackStack()
                        }
                    }

                    is NavigationState.PopAndNavigate -> {
                        navigate(state.destination.fullRoute) {
                            launchSingleTop = state.singleTop
                            popUpTo(0) {
                                inclusive = state.inclusive
                            }
                        }
                    }

                    else -> {}
                }

                onNavigated(state)
            }
        }
    }
}