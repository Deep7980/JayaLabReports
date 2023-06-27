package com.jaya.app.labreports.data.source.remote

import com.jaya.app.labreports.core.model.SendCodeResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SendCodeApi {

    @GET
    suspend fun sendCode(
        @Url url: String,
        @Query("mobile") number: String,
    ): SendCodeResponse


}