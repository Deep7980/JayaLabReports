package com.jaya.app.labreports.presentation.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.labreports.R
import com.jaya.app.labreports.presentation.ui.composables.AppButton
import com.jaya.app.labreports.presentation.ui.composables.VendorQuotationItemView
import com.jaya.app.labreports.presentation.ui.navigation.AppRoute
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.theme.Primary
import com.jaya.app.labreports.presentation.ui.theme.PrimaryVariant
import com.jaya.app.labreports.presentation.ui.theme.Secondary
import com.jaya.app.labreports.presentation.viewModels.MyQuotesViewModel
import com.jaya.app.labreports.presentation.viewstates.QuoteVarianceItem
import com.jaya.app.labreports.presentation.viewstates.VendorQuotationItem
import com.jaya.app.labreports.utilities.Image
import com.jaya.app.labreports.utilities.OnEffect
import com.jaya.app.labreports.utilities.ResponsiveText
import com.jaya.app.labreports.utilities.screenWidth
import com.jaya.app.labreports.utilities.statusBarColor
import kotlinx.coroutines.flow.combine


object MyQuotesScreen : AppRoute<MyQuotesViewModel> {

    override val destination: Destination
        get() = Destination.MyQuotes

    @Composable
    override fun attachedVM(): MyQuotesViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: MyQuotesViewModel) =
        AvailableQuotesScreen(viewModel = viewModel)

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableQuotesScreen(viewModel: MyQuotesViewModel) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarColor(Primary), snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) {
        Column(
            modifier = Modifier.padding(it),

            verticalArrangement = Arrangement.Top
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Lab Reports",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = 50.dp, top = 10.dp)
                )
                AppButton(
                    text = R.string.filter,
                    onBtnClicked = { },
                    modifier = Modifier
                        .padding(start = 120.dp),
                    btnColor = Color.White,
                    textColor = Color.Black
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                viewModel.quoteVariances.collectAsState().value.let { items ->
                    items.forEach { item ->
                        TextButton(
                            onClick = { viewModel.onVarianceClicked(item) },
                            modifier = Modifier
                                .width(screenWidth / items.size)
                                .height(65.dp),
                            shape = RectangleShape,
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = if (item.selected) Color.Yellow.copy(alpha = .5f) else Color.White
                            )
                        ) {
                            item.variance.uiName.ResponsiveText(
                                style = MaterialTheme.typography.caption.copy(
                                    fontWeight = if (item.selected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
            Divider()

            AnimatedContent(targetState = viewModel.quotationsLoading,
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(700)) + slideInHorizontally(animationSpec = tween(
                        500
                    ), initialOffsetX = { it })).togetherWith(
                        fadeOut(
                            animationSpec = tween(700)
                        ) + slideOutHorizontally(
                            animationSpec = tween(500),
                            targetOffsetX = { -it })
                    )
                }) { targetState ->
                when (targetState) {
                    true -> Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center
                        ) {
                            Surface(modifier = Modifier.fillMaxSize(),
                                shape = CircleShape,
                                color = com.jaya.app.labreports.presentation.ui.theme.Surface,
                                content = {})
                            CircularProgressIndicator(
                                color = Secondary, modifier = Modifier.fillMaxSize()
                            )
                            R.drawable.logo.Image(modifier = Modifier.size(100.dp))
                        }
                    }

                    false -> viewModel.quotations.collectAsState().value?.let {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(fraction = .90f)
                                .fillMaxHeight(),
                            contentPadding = PaddingValues(vertical = 15.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            itemsIndexed(
                                items = it,
                                key = { idx, item -> (item.hashCode() + idx) },
                            ) { idx: Int, item: VendorQuotationItem ->
                                VendorQuotationItemView(quotationItem = item,
                                    selectedVariance = viewModel.selectedVariance,
                                    )
                                if (it.last() != item) {
                                    Spacer(modifier = Modifier.height(15.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    EffectHandlers(viewModel, snackbarHostState)
}


@Composable
private fun EffectHandlers(viewModel: MyQuotesViewModel, snackbarHostState: SnackbarHostState) {
    LaunchedEffect(key1 = viewModel.quoteVariances.collectAsState().value) {
        snapshotFlow {
            viewModel.quoteVariances.value
        }

    }

    viewModel.notifier.OnEffect(
        intentionalCode = {
            if (it.isNotEmpty()) {
                snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            }
        },
        clearance = { "" }
    )
}