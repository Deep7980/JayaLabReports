package com.jaya.app.labreports.presentation.viewstates

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.jaya.app.labreports.core.domain.entities.Products

data class VendorQuotationItem(
    val updateState: MutableState<Boolean> = mutableStateOf(false),
    val item: MutableState<Products>
)