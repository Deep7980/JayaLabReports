package com.jaya.app.labreports.utilities

import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.data.source.remote.MyQuotesApi
import javax.inject.Inject

class MyQuotesApiImpl @Inject constructor(

):MyQuotesApi {

    override suspend fun myQuotes(url: String): LabQuotationsResponse {
        TODO("Not yet implemented")
    }
}