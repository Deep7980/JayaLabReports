package com.jaya.app.labreports.data.source.remote

import com.jaya.app.labreports.core.model.labReportsDashboardResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface LabDashboardApi {

    @GET
    suspend fun dashboard(
        @Url url: String
    ): labReportsDashboardResponse
}