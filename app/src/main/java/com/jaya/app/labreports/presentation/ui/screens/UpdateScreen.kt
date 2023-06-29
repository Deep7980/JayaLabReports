package com.jaya.app.labreports.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.labreports.presentation.ui.navigation.AppRoute
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.viewModels.MyQuotesViewModel
import com.jaya.app.labreports.presentation.viewModels.UpdateViewModel


object UpdateScreen : AppRoute<UpdateViewModel> {

    override val destination: Destination
        get() = Destination.Updates

    @Composable
    override fun attachedVM(): UpdateViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: UpdateViewModel) =
        UpdateScreen(viewModel = viewModel)


}

@Composable
fun UpdateScreen(viewModel: UpdateViewModel) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text(
            text = "Product Name",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "Received On : 15 Jan 2023",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Quantity : 15 tons",
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }
}