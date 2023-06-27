package com.jaya.app.labreports.core.model

import com.jaya.app.labreports.core.domain.entities.LabReportDashboardData

data class labReportsDashboardResponse(
    val status: Boolean,
    val message: String,
    val dashboardData: LabReportDashboardData
)
