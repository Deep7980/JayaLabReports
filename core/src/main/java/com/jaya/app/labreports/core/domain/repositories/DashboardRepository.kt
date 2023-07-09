package com.jaya.app.labreports.core.domain.repositories

import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.entities.VendorCredentials
import com.jaya.app.labreports.core.model.VendorCredentialsResponse

import com.jaya.app.labreports.core.model.labReportsDashboardResponse

interface DashboardRepository {

//    suspend fun labReportsDashboard():Response<labReportsDashboardResponse>

//    suspend fun getMenus():Response<MenuItems>

    suspend fun getVendorCredentials():Response<VendorCredentialsResponse>


}