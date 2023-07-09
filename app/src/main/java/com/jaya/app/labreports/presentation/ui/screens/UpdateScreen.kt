package com.jaya.app.labreports.presentation.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.labreports.R
import com.jaya.app.labreports.core.domain.entities.ItemIdToUpdate
import com.jaya.app.labreports.presentation.ui.composables.AppButton
import com.jaya.app.labreports.presentation.ui.navigation.AppRoute
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.theme.Primary
import com.jaya.app.labreports.presentation.ui.theme.Secondary
import com.jaya.app.labreports.presentation.viewModels.UpdateViewModel
import com.jaya.app.labreports.utilities.BackPressHandler
import com.jaya.app.labreports.utilities.Image
import com.jaya.app.labreports.utilities.Text
import com.jaya.app.labreports.utilities.statusBarColor


object UpdateScreen : AppRoute<UpdateViewModel> {

    override val destination: Destination
        get() = Destination.Updates

    @Composable
    override fun attachedVM(): UpdateViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: UpdateViewModel) =
        UpdateScreen(viewModel = viewModel)


}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun UpdateScreen(viewModel: UpdateViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getSpecificProductsList(ItemIdToUpdate.IdToUpdate)

    }
    
    BackPressHandler(onBackPressed = {viewModel.onBackPress()})

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarColor(color = Primary),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        topBar = { TopBarSection(viewModel::NavigateBack,viewModel) },

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


        AnimatedContent(
            targetState = viewModel.quotationsLoading,
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            transitionSpec = {
                fadeIn(animationSpec = tween(700)) + slideInVertically(animationSpec = tween(400),
                    initialOffsetY = { fullHeight -> fullHeight }) with
                        fadeOut(animationSpec = tween(400))
            }
        ) {targetState ->
            when(targetState){
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
                false ->     Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        text = viewModel.productName.value,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = "Received on : ${viewModel.productReceiveDate.value}",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Quantity : ${viewModel.productQuantity.value}",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(1f)
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
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .background(Color.DarkGray)
                                .fillMaxWidth(1f)
                                .fillMaxHeight(.10f)
                        ) {
                            R.string.parameters.Text(
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color.White,

                                    ),modifier = Modifier.padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                    bottom = 20.dp,
                                    top = 15.dp
                                )
                            )
                            Text(
                                text = "Value",
                                fontSize = 15.sp,
                                color = Color.White,
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                    bottom = 20.dp,
                                    top = 15.dp
                                )
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            PreviewItem(viewModel)
                        }
                        //MaterialListDisplay()
                        AppButton(
                            text = R.string.submit_report,
                            onBtnClicked = viewModel::NavigateToReportSubmit,
                            btnColor = Color.Green,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .fillMaxHeight(0.20f)
                                .padding(bottom = 10.dp, top = 40.dp, start = 20.dp, end = 20.dp)
                        )
                    }
                }
            }
        }


//    TopBarSection()

}
}
}



@Composable
fun PreviewItem(viewModel: UpdateViewModel){
//    MaterialListDisplay(title = "Moisture", desc = "% by Mass, Max", value = "0.0")

    Column(modifier = Modifier.verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
//        viewModel.materialList.collectAsState().value.forEach{item->
//            MaterialListDisplay(title = item,viewModel)
//
//        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(1f)
        ){
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, end = 10.dp, bottom = 5.dp)
                    .fillMaxWidth()
            ) {
                viewModel.materialList.collectAsState().value.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = item, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        val initialVal = remember{mutableStateOf("13.0")}
                        OutlinedTextField(
                            value = initialVal.value,
                            onValueChange = { text ->
                                if (text.length <= 5)
                                    initialVal.value = text
                            },
                            singleLine = true,
                            placeholder = { ("0.00") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(50.dp)
                                .padding(end = 10.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number
                            ),
                        )
                    }
                    Divider(thickness = 0.80.dp, color = Color.DarkGray, modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth())
                }

//            viewModel.materialDesc.collectAsState().value.forEach {desc->
//                Text(text = desc, fontWeight = FontWeight.Light, fontSize = 16.sp)
//            }

            }


        }


    }

}





@Composable
fun MaterialListDisplay(title: String,viewModel: UpdateViewModel) {
    val initialVal = remember{mutableStateOf("13.0")}
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(1f)
    ){
        Column(
            modifier = Modifier.padding(start = 20.dp,top=10.dp, end = 10.dp, bottom = 5.dp)
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
//            viewModel.materialDesc.collectAsState().value.forEach {desc->
//                Text(text = desc, fontWeight = FontWeight.Light, fontSize = 16.sp)
//            }

        }
        OutlinedTextField(
            value = initialVal.value,
            onValueChange = { text->
                if (text.length<=5)
                    initialVal.value = text
            },
            singleLine = true,
            placeholder = { ("0.00") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier
                .width(80.dp)
                .height(60.dp)
                .padding(end = 10.dp, top = 10.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
        )
    }
    Divider(thickness = 0.50.dp, color = Color.DarkGray, modifier = Modifier.padding( top = 10.dp))
}

@Composable
fun TopBarSection(navigateBack: () -> Unit,viewModel: UpdateViewModel) {
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
                painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        viewModel.NavigateBack()
                    }

            )
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
            Image(painter = painterResource(id = R.drawable.bell), contentDescription = null)
        }

    }
}
