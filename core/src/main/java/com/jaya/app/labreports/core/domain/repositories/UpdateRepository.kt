package com.jaya.app.labreports.core.domain.repositories

import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.model.LabQuotationsResponse
import retrofit2.http.Query

interface UpdateRepository {

    suspend fun getItemDetails(id:String):Response<LabQuotationsResponse>
}