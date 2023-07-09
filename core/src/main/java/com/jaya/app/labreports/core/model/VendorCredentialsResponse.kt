package com.jaya.app.labreports.core.model

import com.jaya.app.labreports.core.domain.entities.VendorCredentials

data class VendorCredentialsResponse(
    val status:Boolean,
    val message:String,
    val vendorCredentials: VendorCredentials
)
