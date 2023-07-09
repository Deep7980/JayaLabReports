package com.jaya.app.labreports.data.source.remote

import com.jaya.app.labreports.core.domain.entities.VendorCredentials
import com.jaya.app.labreports.core.model.VendorCredentialsResponse
import com.jaya.app.labreports.core.model.labReportsDashboardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface LabDashboardApi {

    @GET
    suspend fun dashboard(
        @Url url: String
    ): labReportsDashboardResponse

    @GET
    suspend fun getVendorCredentials(@Url url: String):VendorCredentialsResponse
}