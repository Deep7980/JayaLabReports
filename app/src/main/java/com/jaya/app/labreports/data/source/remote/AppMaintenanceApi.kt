package com.jaya.app.labreports.data.source.remote

import com.jaya.app.labreports.core.model.AppMaintenanceResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface AppMaintenanceApi {
    @GET
    suspend fun maintainApp(
        @Url url: String,
        @Query("versionCode") currentVersion: Int,
    ): AppMaintenanceResponse


}