package com.jaya.app.labreports.data.source.remote

import com.jaya.app.labreports.core.model.VerifyCodeResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface VerifyCodeApi {

    @GET
    suspend fun verify(
        @Url url: String,
        @Query("mobile") mobileNumber: String,
        @Query("otp") code: String
    ): VerifyCodeResponse
}