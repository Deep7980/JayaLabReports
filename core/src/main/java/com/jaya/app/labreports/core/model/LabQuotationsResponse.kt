package com.jaya.app.labreports.core.model

import com.google.gson.annotations.SerializedName
import com.jaya.app.labreports.core.domain.entities.Products

data class LabQuotationsResponse(
    val status: Boolean,
    val message: String,
    val products: List<Products>
)
