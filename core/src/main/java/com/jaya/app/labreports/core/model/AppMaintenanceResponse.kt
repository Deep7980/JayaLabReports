package com.jaya.app.labreports.core.model

import com.jaya.app.labreports.core.domain.entities.AppVersion
import com.jaya.app.labreports.core.domain.entities.Domain

data class AppMaintenanceResponse(
    val status: Boolean,
    val message: String,
    val version: AppVersion,
    val domain: Domain
)
