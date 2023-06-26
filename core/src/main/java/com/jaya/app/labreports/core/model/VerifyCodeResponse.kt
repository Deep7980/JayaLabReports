package com.jaya.app.labreports.core.model

import com.jaya.app.labreports.core.domain.entities.VendorCredentials

data class VerifyCodeResponse(
    val status: Boolean,
    val message: String,
    val vendorCreds: VendorCredentials
)
