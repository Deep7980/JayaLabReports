package com.jaya.app.labreports.presentation.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.labreports.R
import com.jaya.app.labreports.core.domain.entities.ItemIdToUpdate
import com.jaya.app.labreports.core.domain.entities.getMaterialList
import com.jaya.app.labreports.presentation.ui.composables.AppButton
import com.jaya.app.labreports.presentation.ui.navigation.AppRoute
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.theme.Primary
import com.jaya.app.labreports.presentation.viewModels.DashBoardViewModel
import com.jaya.app.labreports.presentation.viewModels.MyQuotesViewModel
import com.jaya.app.labreports.presentation.viewModels.UpdateViewModel
import com.jaya.app.labreports.utilities.Text
import com.jaya.app.labreports.utilities.statusBarColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


object UpdateScreen : AppRoute<UpdateViewModel> {

    override val destination: Destination
        get() = Destination.Updates

    @Composable
    override fun attachedVM(): UpdateViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: UpdateViewModel) =
        UpdateScreen(viewModel = viewModel)


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(viewModel: UpdateViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getSpecificProductsList(ItemIdToUpdate.IdToUpdate)

    }

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


//    TopBarSection()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text(
            text = "Maida",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "Received on : 15 Jan 2023",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Quantity : 15 Tons",
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
                    .background(Color.LightGray)
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
            //MaterialListDisplay()
            PreviewItem(viewModel)

        }
    }
}
}
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

@Composable
fun PreviewItem(viewModel: UpdateViewModel){
//    MaterialListDisplay(title = "Moisture", desc = "% by Mass, Max", value = "0.0")
    Column(modifier = Modifier.verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        getMaterialList.materialList().map { item->
            MaterialListDisplay(title = item.materials[0], desc = item.desc[0])
            MaterialListDisplay(title = item.materials[1], desc = item.desc[1])
            MaterialListDisplay(title = item.materials[2], desc = item.desc[2])
            MaterialListDisplay(title = item.materials[3], desc = item.desc[3])
            MaterialListDisplay(title = item.materials[4], desc = item.desc[4])
            MaterialListDisplay(title = item.materials[5], desc = item.desc[5])
            MaterialListDisplay(title = item.materials[6], desc = item.desc[6])
        }
        AppButton(
            text = R.string.submit_report,
            onBtnClicked = viewModel::NavigateToReportSubmit,
            btnColor = Color.Green,
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .fillMaxHeight(0.90f)
                .padding(bottom = 10.dp, top = 5.dp)


        )

    }
//    LazyColumn(content = {
//        items(getMaterialList.materialList()){item->
//            MaterialListDisplay(title = item.materials[0], desc = item.desc[0], value = item.values[0])
//        }
//    })

}


@Composable
fun MaterialListDisplay(title:String, desc:String) {
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
            Text(text = desc, fontWeight = FontWeight.Light, fontSize = 16.sp)
        }
        OutlinedTextField(
            value = initialVal.value,
            onValueChange = { text->
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
