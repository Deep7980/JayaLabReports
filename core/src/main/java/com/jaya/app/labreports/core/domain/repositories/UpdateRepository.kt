package com.jaya.app.labreports.core.domain.repositories

import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.entities.Materials
import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.core.model.MaterialDetailResponse
import retrofit2.http.Query

interface UpdateRepository {

    suspend fun getItemDetails(id:String):Response<LabQuotationsResponse>

    suspend fun getMaterials():Response<MaterialDetailResponse>

}