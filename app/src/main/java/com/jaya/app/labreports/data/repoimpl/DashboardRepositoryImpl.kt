package com.jaya.app.labreports.data.repoimpl

import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.DashboardRepository
import com.jaya.app.labreports.core.model.labReportsDashboardResponse
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.data.source.remote.LabDashboardApi
import retrofit2.Retrofit
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val preference: AppPreference
):DashboardRepository {
    override suspend fun labReportsDashboard(): Response<labReportsDashboardResponse> {
        return try {
            val baseUrl = "https://api.npoint.io"
            val url = if (baseUrl != null) {
                "$baseUrl/0dfb3f9001090cec4eee"
            } else {
                "/0dfb3f9001090cec4eee"
            }
            val result = retrofit
                .create(LabDashboardApi::class.java)
                .dashboard(url)
            Response.Success(result)
        }  catch (ex: Exception) {
            Response.Error(message = ex.message)
        } finally {
            Response.Loading<labReportsDashboardResponse>(state = false)
        }
    }
}

//https://api.npoint.io/0dfb3f9001090cec4eee
//https://api.npoint.io/0dfb3f9001090cec4eee
//https://api.npoint.io/0dfb3f9001090cec4eee
//https://api.npoint.io/0dfb3f9001090cec4eee