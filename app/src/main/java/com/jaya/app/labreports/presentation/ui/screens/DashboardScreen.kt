package com.jaya.app.labreports.presentation.ui.screens

import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jaya.app.labreports.R
import com.jaya.app.labreports.core.domain.entities.VendorCredentials
import com.jaya.app.labreports.presentation.ui.navigation.AppRoute
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.Graph
import com.jaya.app.labreports.presentation.ui.navigation.graph.dashboardNavGraph
import com.jaya.app.labreports.presentation.ui.navigation.graph.mainNavGraph
import com.jaya.app.labreports.presentation.ui.theme.Primary
import com.jaya.app.labreports.presentation.viewModels.DashBoardViewModel
import com.jaya.app.labreports.presentation.viewModels.MainViewModel
import com.jaya.app.labreports.presentation.viewstates.DrawerMenuItem
import com.jaya.app.labreports.presentation.viewstates.DrawerMenus
import com.jaya.app.labreports.utilities.Image
import com.jaya.app.labreports.utilities.Text
import com.jaya.app.labreports.utilities.screenWidth
import com.jaya.app.labreports.utilities.statusBarColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


object DashboardScreen : AppRoute<DashBoardViewModel> {
    override val destination: Destination
        get() = Destination.Dashboard

    @Composable
    override fun attachedVM(): DashBoardViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: DashBoardViewModel) = DashboardScreen(viewModel)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashBoardViewModel) {

    val dashboardDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val uiScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            DashboardDrawerSection(
                drawerMenus = viewModel.drawerMenus.collectAsState(),
                vendorDetails = viewModel.vendorDetails.collectAsState(),
                onSelect = viewModel::onDrawerMenuClicked,
                closeDrawer = {
                    uiScope.launch {
                        when (dashboardDrawerState.currentValue) {
                            DrawerValue.Closed -> {}
                            DrawerValue.Open -> {
                                dashboardDrawerState.animateTo(
                                    targetValue = DrawerValue.Closed,
                                    anim = tween(500)
                                )
                            }
                        }
                    }
                })
        }, drawerState = dashboardDrawerState
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarColor(color = Primary),
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            topBar = { TopBarSection(dashboardDrawerState, uiScope) },

            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                MainContent(viewModel)
            }
        }
    }

    EffectHandlers(viewModel = viewModel)

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardDrawerSection(
    drawerMenus: State<List<DrawerMenuItem>>,
    vendorDetails: State<VendorCredentials?>,
    onSelect: (Int, DrawerMenuItem) -> Unit,
    closeDrawer: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = Modifier.width(screenWidth * .75f), drawerContainerColor = Primary
    ) {
        Spacer(Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            when (vendorDetails.value != null) {
                true -> {
                    vendorDetails.value?.apply {

                    }
                }

                false -> {
                    R.drawable.logo.Image()
                    R.string.app_name_new.Text(
                        style = MaterialTheme.typography.h4.copy(
                            color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
        Divider(color = Color.White)
        Spacer(Modifier.height(12.dp))
        drawerMenus.value.forEachIndexed { index, drawerMenuItem ->
            NavigationDrawerItem(label = {
                drawerMenuItem.menu.mName.Text(
                    style = MaterialTheme.typography.h6
                )
            }, selected = drawerMenuItem.selected, onClick = {
                coroutineScope.launch {
                    closeDrawer()
                    delay(700)
                    onSelect(index, drawerMenuItem)
                }
            }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding), icon = {
                if (drawerMenuItem.menu == DrawerMenus.Logout) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                } else {
                    drawerMenuItem.menu.mIcon.Image(modifier = Modifier.size(24.dp))
                }
            })
            if (drawerMenus.value.last() != drawerMenuItem) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainContent(viewModel: DashBoardViewModel) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Graph.Dashboard.fullRoute,
    ) {
        dashboardNavGraph(navController = navController, parentViewModel = viewModel)
    }
}

@Composable
private fun EffectHandlers(viewModel: DashBoardViewModel) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarSection(dashboardDrawerState: DrawerState, uiScope: CoroutineScope) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        elevation = 10.dp,
        contentPadding = PaddingValues(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.hamburgar),
                contentDescription = null,
                modifier = Modifier.clickable {
                    uiScope.launch {
                        when (dashboardDrawerState.currentValue) {
                            DrawerValue.Closed -> dashboardDrawerState.animateTo(
                                targetValue = DrawerValue.Open, anim = tween(700)
                            )

                            DrawerValue.Open -> dashboardDrawerState.animateTo(
                                targetValue = DrawerValue.Closed, anim = tween(700)
                            )
                        }
                    }
                })
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
            Image(painter = painterResource(id = R.drawable.bell), contentDescription = null)
        }

    }
}



