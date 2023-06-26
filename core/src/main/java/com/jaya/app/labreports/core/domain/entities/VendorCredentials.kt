package com.jaya.app.labreports.core.domain.entities

data class VendorCredentials(
    val id: String,
    val token: String,
    val name: String,
    val type: String,
    val companyName: String,
    val isVerified: Boolean
)
