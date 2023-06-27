package com.jaya.app.labreports.presentation.viewstates

import com.jaya.app.labreports.core.common.enums.QuotationVariance

data class QuoteVarianceItem(
    val selected: Boolean = false,
    val variance: QuotationVariance
)
