package com.jaya.app.labreports.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.jaya.app.labreports.R
import com.jaya.app.labreports.presentation.ui.composables.AppButton
import com.jaya.app.labreports.presentation.ui.navigation.AppRoute
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.theme.Primary
import com.jaya.app.labreports.presentation.viewModels.MyQuotesViewModel
import com.jaya.app.labreports.utilities.OnEffect
import com.jaya.app.labreports.utilities.statusBarColor
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.Navigation
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.HorizontalPager
import com.jaya.app.labreports.presentation.ui.theme.PurpleGrey40
import com.jaya.app.labreports.presentation.ui.theme.Secondary
import com.jaya.app.labreports.utilities.ResponsiveText
import com.jaya.app.labreports.utilities.Text
import kotlinx.coroutines.launch


object MyQuotesScreen : AppRoute<MyQuotesViewModel> {

    override val destination: Destination
        get() = Destination.MyQuotes

    @Composable
    override fun attachedVM(): MyQuotesViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: MyQuotesViewModel) =
        AvailableQuotesScreen(viewModel = viewModel)

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AvailableQuotesScreen(viewModel: MyQuotesViewModel) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    // on below line we are creating variable for pager state.

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
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                viewModel.quoteVariances.collectAsState().value.let { items ->
//                    items.forEach { item ->
//                        TextButton(
//                            onClick = { viewModel.onVarianceClicked(item) },
//                            modifier = Modifier
//                                .width(screenWidth / items.size)
//                                .height(65.dp),
//                            shape = RectangleShape,
//                            colors = ButtonDefaults.textButtonColors(
//                                containerColor = if (item.selected) Color.Yellow.copy(alpha = .5f) else Color.White
//                            )
//                        ) {
//                            item.variance.uiName.ResponsiveText(
//                                style = MaterialTheme.typography.caption.copy(
//                                    fontWeight = if (item.selected) FontWeight.Bold else FontWeight.Normal
//                                )
//                            )
//                        }
//                    }
//                }
//            }
//            Divider()

//            AnimatedContent(targetState = viewModel.quotationsLoading,
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center,
//                transitionSpec = {
//                    (fadeIn(animationSpec = tween(700)) + slideInHorizontally(animationSpec = tween(
//                        500
//                    ), initialOffsetX = { it })).togetherWith(
//                        fadeOut(
//                            animationSpec = tween(700)
//                        ) + slideOutHorizontally(
//                            animationSpec = tween(500),
//                            targetOffsetX = { -it })
//                    )
//                }) { targetState ->
//                when (targetState) {
//                    true -> Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Box(
//                            modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center
//                        ) {
//                            Surface(modifier = Modifier.fillMaxSize(),
//                                shape = CircleShape,
//                                color = com.jaya.app.labreports.presentation.ui.theme.Surface,
//                                content = {})
//                            CircularProgressIndicator(
//                                color = Secondary, modifier = Modifier.fillMaxSize()
//                            )
//                            R.drawable.logo.Image(modifier = Modifier.size(100.dp))
//                        }
//                    }
//
//                    false -> viewModel.quotations.collectAsState().value?.let {
//                        LazyColumn(
//                            modifier = Modifier
//                                .fillMaxWidth(fraction = .90f)
//                                .fillMaxHeight(),
//                            contentPadding = PaddingValues(vertical = 15.dp),
//                            verticalArrangement = Arrangement.Top,
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                        ) {
//                            itemsIndexed(
//                                items = it,
//                                key = { idx, item -> (item.hashCode() + idx) },
//                            ) { idx: Int, item: VendorQuotationItem ->
//                                VendorQuotationItemView(quotationItem = item,
//                                    selectedVariance = viewModel.selectedVariance,
//                                    )
//                                if (it.last() != item) {
//                                    Spacer(modifier = Modifier.height(15.dp))
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//            }
//            Tabs(pagerState = pagerState)
//            TabsContent(pagerState = pagerState)
            TabScreen(viewModel)

            if(viewModel.quotationsLoading){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(150.dp)
                ) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        strokeWidth = 1.dp,
                        modifier = Modifier
                            .size(48.dp)
//                            .padding(top = 100.dp, start = 40.dp)

                    )
                    R.string.loading.Text(
                        style = androidx.compose.material.MaterialTheme.typography.body1,
//                        modifier = Modifier.padding(start = 100.dp, end = 50.dp,top=100.dp)

                    )
                }
            }
//            viewModel.ProductsList.collectAsState().value.forEach {
//                if(it!=null){
//                    Text(text = it.name)
//                }
//            }
        }

    }
    EffectHandlers(viewModel, snackbarHostState)
}


@Composable
fun TabScreen(viewModel:MyQuotesViewModel) {
    var tabIndex by remember { mutableStateOf(0) }


    val tabs = listOf("New Entry", "Report Submitted")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = {
                    Text(title,
                        fontWeight = if(tabIndex == index) FontWeight.Bold else FontWeight.Light,
                        fontSize = if(tabIndex == index) 18.sp else 16.sp

                    )
                           },
                    selected = tabIndex == index,
                    selectedContentColor = if(tabIndex == index) Color.Black else Color.Black,
                    modifier = Modifier.background(if(tabIndex == index) Color.Yellow else Color.White),
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> NewEntryScreen(viewModel)
            1 -> ReportSubmittedScreen(viewModel)
        }
    }
}

@Composable
fun NewEntryScreen(viewModel: MyQuotesViewModel) {


    if(!viewModel.quotationsLoading){
        viewModel.ProductsList.collectAsState().value.forEach {

            LazyColumn() {
                itemsIndexed(viewModel.ProductsList.value.toList()) { index, item ->

                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(30.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, color = Color.LightGray),
                        elevation = CardDefaults.outlinedCardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 20.dp, start = 20.dp)
                                )
                                Text(
                                    text = "Received On : ${item.receivedOn}",
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(top = 10.dp, start = 20.dp)
                                )
                            }
                            Image(
                                painter = rememberAsyncImagePainter(item.image),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(120.dp)
                                    .padding(bottom = 30.dp)
                            )

                        }
                        Divider(
                            thickness = 1.dp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(70.dp)
                                .padding(start = 20.dp, bottom = 20.dp, end = 20.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = Color.LightGray
                            ),
                            border = BorderStroke(1.dp, color = Color.LightGray),
                            elevation = CardDefaults.outlinedCardElevation(
                                defaultElevation = 10.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier
                            ) {

//                        R.string.update_lab_reports.Text(
//
//                            style = TextStyle(Color.White, fontSize = 18.sp,fontWeight = FontWeight.Medium),
//                            modifier = Modifier.padding(start = 20.dp, top = 13.dp)
//                        )
//
//                        Image(
//                            painter = painterResource(id = R.drawable.arrow),
//                            contentDescription = "",
//                            modifier = Modifier
//                                .width(90.dp)
//                                .height(70.dp)
//                                .padding(start = 60.dp)
//
//                        )
                                AppButton(
                                    text = R.string.update_lab_reports,
                                    onBtnClicked = { viewModel.updateScreen(item.id) },
                                    btnColor = Color.LightGray,
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(70.dp)
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.arrow),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .width(90.dp)
                                        .height(70.dp)

                                )
                            }
                        }
                    }
                }
            }

        }
    }

}

@Composable
fun ReportSubmittedScreen(viewModel: MyQuotesViewModel) {

    viewModel.ProductsList.collectAsState().value.forEach {

        LazyColumn() {
            itemsIndexed(viewModel.ProductsList.value.toList()) { index, item ->

                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(30.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(1.dp, color = Color.LightGray),
                    elevation = CardDefaults.outlinedCardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {

                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text(
                                text = item.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 20.dp, start = 20.dp)
                            )
                            Text(
                                text = "Received On : ${item.receivedOn}",
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(
                                    top = 10.dp,
                                    start = 20.dp,
                                    bottom = 20.dp
                                )
                            )
                        }
                        Image(
                            painter = rememberAsyncImagePainter(it.image),
                            contentDescription = "",
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .padding(bottom = 30.dp)
                        )
                    }
                    Divider(
                        thickness = 1.dp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(70.dp)
                            .padding(start = 20.dp, bottom = 20.dp, end = 20.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = Color.Green
                        ),
                        border = BorderStroke(1.dp, color = Color.LightGray),
                        elevation = CardDefaults.outlinedCardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_check_circle_24),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                                    .padding(start = 20.dp)

                            )

                            R.string.update_lab_reports.Text(

                                style = TextStyle(
                                    Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.padding(start = 20.dp, top = 13.dp)
                            )
                        }
                    }
                }
            }
        }
    }

}




















//@OptIn(ExperimentalFoundationApi::class)
//@ExperimentalPagerApi
//@Composable
//fun Tabs(pagerState: PagerState){
//
//    val list = listOf(
//        "New Entry" ,
//        "Report Submitted"
//    )
//
//    val scope = rememberCoroutineScope()
//    TabRow(
//        selectedTabIndex = pagerState.currentPage,
//        contentColor = Color.White,
//        containerColor = Color.Yellow,
//        indicator = { tabPositions ->
//            // on below line we are specifying the styling
//            // for tab indicator by specifying height
//            // and color for the tab indicator.
//            TabRowDefaults.Indicator(
////               modifier = Modifier.pagerTabIndicatorOffset(pagerState,tabPositions),
//                height = 2.dp,
//                color = Color.Green
//            )
//        }
//    ) {
//        list.forEachIndexed { index, _ ->
//            // on below line we are creating a tab.
//            Tab(
//                text = {
////                    Text(
////                        list[index].first(),
////                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
////                    )
//                       Text(text = list[index],color = if (pagerState.currentPage == index) Color.Black else Color.LightGray)
//                },
//                // on below line we are specifying
//                // the tab which is selected.
//                selected = pagerState.currentPage == index,
//                // on below line we are specifying the
//                // on click for the tab which is selected.
//                onClick = {
//                    // on below line we are specifying the scope.
//                    scope.launch {
//                        pagerState.animateScrollToPage(index)
//                    }
//                }
//            )
//        }
//    }
//}
//
//@ExperimentalPagerApi
//@Composable
//fun TabsContent(pagerState: PagerState) {
//    // on below line we are creating
//    // horizontal pager for our tab layout.
//    HorizontalPager(pagerState) {
//        // on below line we are specifying
//        // the different pages.
//            page ->
//        when (page) {
//            // on below line we are calling tab content screen
//            // and specifying data as Home Screen.
//            0 -> TabContentScreen(data = "Welcome to Home Screen")
//            // on below line we are calling tab content screen
//            // and specifying data as Shopping Screen.
//            1 -> TabContentScreen(data = "Welcome to Shopping Screen")
//            // on below line we are calling tab content screen
//            // and specifying data as Settings Screen.
//        }
//    }
//}
//
//@Composable
//fun TabContentScreen(data: String) {
//    // on below line we are creating a column
//    Column(
//        // in this column we are specifying modifier
//        // and aligning it center of the screen on below lines.
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // in this column we are specifying the text
//        Text(
//            // on below line we are specifying the text message
//            text = data,
//
//            // on below line we are specifying the text style.
//            style = MaterialTheme.typography.displayMedium,
//
//            // on below line we are specifying the text color
//            color = Primary,
//
//            // on below line we are specifying the font weight
//            fontWeight = FontWeight.Bold,
//
//            //on below line we are specifying the text alignment.
//            textAlign = TextAlign.Center
//        )
//    }
//}


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