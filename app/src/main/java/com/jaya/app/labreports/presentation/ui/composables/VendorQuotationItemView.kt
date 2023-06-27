package com.jaya.app.labreports.presentation.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jaya.app.labreports.R
import com.jaya.app.labreports.core.common.enums.QuotationVariance
import com.jaya.app.labreports.presentation.ui.theme.Rejected
import com.jaya.app.labreports.presentation.viewstates.VendorQuotationItem
import com.jaya.app.labreports.utilities.ResponsiveText


@Composable
fun VendorQuotationItemView(
    quotationItem: VendorQuotationItem, selectedVariance: QuotationVariance
) {

    var firstPortionSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    var secondPortionSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    var itemWidth by remember {
        mutableStateOf(IntSize.Zero)
    }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(.90f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (selectedVariance == QuotationVariance.NewEntry) Color.White else Rejected
        ),
        border = BorderStroke(1.dp, color = Color.LightGray),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 10.dp
        )
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            val context = LocalContext.current
            quotationItem.item.value.apply {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .onGloballyPositioned {
                                firstPortionSize = it.size
                            }
                            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        name.ResponsiveText(
                            style = MaterialTheme.typography.h4, modifier = Modifier
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            R.string.received_on.ResponsiveText(
                                style = MaterialTheme.typography.h6.copy(
                                    color = Color.LightGray
                                )
                            )
                            " : ${quotationItem.item.value.receivedOn}".ResponsiveText(
                                style = MaterialTheme.typography.h6.copy(
                                    color = Color.Gray, fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }
                }
            }
        }

    }


}