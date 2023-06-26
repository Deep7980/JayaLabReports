package com.jaya.app.labreports.presentation.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jaya.app.labreports.presentation.ui.navigation.Graph
import com.jaya.app.labreports.presentation.ui.navigation.graph.mainNavGraph
import com.jaya.app.labreports.presentation.ui.theme.JayaLabReportsTheme
import com.jaya.app.labreports.presentation.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onStart() {
        super.onStart()
        viewModel.connectivity.listeningNetworkState()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JayaLabReportsTheme {
                val navController = rememberAnimatedNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Graph.Main.fullRoute,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        mainNavGraph(navController = navController, baseViewModel = viewModel)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.connectivity.stopListenNetworkState()
    }
}
