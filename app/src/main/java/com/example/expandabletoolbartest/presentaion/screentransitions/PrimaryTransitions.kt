package com.example.expandabletoolbartest.presentaion.screentransitions

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.example.expandabletoolbartest.presentaion.screens.destinations.ChooseScrollBehaviourScreenDestination
import com.example.expandabletoolbartest.presentaion.screens.destinations.ScrollScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.utils.destination

@OptIn(ExperimentalAnimationApi::class)
object PrimaryTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.destination()) {
            ChooseScrollBehaviourScreenDestination -> {
                slideInVertically(
                    initialOffsetY = { 0 },
                    animationSpec = tween(700)
                )
            }
            ScrollScreenDestination -> {
                slideInVertically(
                    initialOffsetY = { 0 },
                    animationSpec = tween(700)
                )
            }
            else -> EnterTransition.None
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return when (initialState.destination()) {
            ChooseScrollBehaviourScreenDestination -> {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(700)
                )
            }
            ScrollScreenDestination -> {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(700)
                )
            }
            else -> ExitTransition.None
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return when (initialState.destination()) {
            ChooseScrollBehaviourScreenDestination -> {
                slideInVertically(
                    initialOffsetY = {0},
                    animationSpec = tween(700)
                )
            }
            ScrollScreenDestination -> {
                slideInVertically(
                    initialOffsetY = {0},
                    animationSpec = tween(700)
                )
            }
            else -> EnterTransition.None
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (initialState.destination()) {
            ChooseScrollBehaviourScreenDestination -> {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(700)
                )
            }
            ScrollScreenDestination -> {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(700)
                )
            }
            else -> ExitTransition.None
        }
    }
}