package com.jaya.app.labreports.data.source.remote

import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.core.model.labReportsDashboardResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MyQuotesApi {

//    @GET
//    suspend fun myQuotes(
//        @Url url: String,
//        @Query("status") variance: String
//    ): LabQuotationsResponse

    @GET
    suspend fun myQuotes(
        @Url url: String,
//        @Query("status") variance: String
    ): LabQuotationsResponse
}