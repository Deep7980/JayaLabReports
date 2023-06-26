package com.jaya.app.labreports.presentation.ui.navigation.screen_transitions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.jaya.app.labreports.presentation.ui.navigation.Destination

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition {

    return when (initialState.destination.route) {
//        Destination.MyQuotes.fullRoute -> {
//            slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Up,
//                tween(700),
//                initialOffset = { it }
//            )
//        }
//
//        Destination.Orders.fullRoute -> {
//            slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Up,
//                tween(700),
//                initialOffset = { it }
//            )
//        }
//
//        Destination.Completed.fullRoute -> {
//            slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Up,
//                tween(700),
//                initialOffset = { it }
//            )
//        }

        else -> {
            slideInHorizontally(
                tween(300, easing = FastOutSlowInEasing),
                initialOffsetX = { it })
        }

    }
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition = when(initialState.destination.route) {
//    Destination.MyQuotes.fullRoute -> {
//        slideOutOfContainer(
//            AnimatedContentTransitionScope.SlideDirection.Down,
//            tween(700),
//            targetOffset = { it }
//        )
//    }
//    Destination.Completed.fullRoute -> {
//        slideOutOfContainer(
//            AnimatedContentTransitionScope.SlideDirection.Down,
//            tween(700),
//            targetOffset = { it }
//        )
//    }
//    Destination.Orders.fullRoute -> {
//        slideOutOfContainer(
//            AnimatedContentTransitionScope.SlideDirection.Down,
//            tween(700),
//            targetOffset = { it }
//        )
//    }
    else -> {
        slideOutHorizontally(
            tween(300, easing = FastOutSlowInEasing),
            targetOffsetX = { -it })
    }
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition = when(initialState.destination.route) {
//    Destination.MyQuotes.fullRoute -> {
//        slideIntoContainer(
//            AnimatedContentTransitionScope.SlideDirection.Up,
//            tween(700),
//            initialOffset = { it }
//        )
//    }
//    Destination.Completed.fullRoute -> {
//        slideIntoContainer(
//            AnimatedContentTransitionScope.SlideDirection.Up,
//            tween(700),
//            initialOffset = { it }
//        )
//    }
//    Destination.Orders.fullRoute -> {
//        slideIntoContainer(
//            AnimatedContentTransitionScope.SlideDirection.Up,
//            tween(700),
//            initialOffset = { it }
//        )
//    }
    else ->{
        slideInHorizontally(
            tween(
                300,
                easing = FastOutSlowInEasing
            ),
            initialOffsetX = { -it }
        )
    }
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition = when(initialState.destination.route) {
//    Destination.MyQuotes.fullRoute -> {
//        slideOutOfContainer(
//            AnimatedContentTransitionScope.SlideDirection.Down,
//            tween(700),
//            targetOffset = { it }
//        )
//    }
//    Destination.Completed.fullRoute -> {
//        slideOutOfContainer(
//            AnimatedContentTransitionScope.SlideDirection.Down,
//            tween(700),
//            targetOffset = { it }
//        )
//    }
//    Destination.Orders.fullRoute -> {
//        slideOutOfContainer(
//            AnimatedContentTransitionScope.SlideDirection.Down,
//            tween(700),
//            targetOffset = { it }
//        )
//    }
    else -> {
        slideOutHorizontally(
            tween(300, easing = FastOutSlowInEasing),
            targetOffsetX = { it })
    }
}


