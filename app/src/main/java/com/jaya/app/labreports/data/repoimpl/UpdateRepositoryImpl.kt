package com.jaya.app.labreports.data.repoimpl

import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.UpdateRepository
import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.core.utilities.AppPreference
import javax.inject.Inject

class UpdateRepositoryImpl @Inject constructor(
    private val preference: AppPreference
):UpdateRepository{
    override suspend fun getItemDetails(id: String): Response<LabQuotationsResponse> {
        TODO("Not yet implemented")
    }
}