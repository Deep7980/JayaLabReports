package com.jaya.app.labreports.data.repoimpl

import android.util.Log
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.entities.VendorCredentials
import com.jaya.app.labreports.core.domain.repositories.DashboardRepository
import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.core.model.VendorCredentialsResponse
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.data.source.remote.LabDashboardApi
import com.jaya.app.labreports.data.source.remote.UpdateProductsApi
import retrofit2.Retrofit
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val preference: AppPreference,
    private val httpClient: Retrofit,
):DashboardRepository {
    //    override suspend fun labReportsDashboard(): Response<labReportsDashboardResponse> {
//        return try {
//            val baseUrl = "https://api.npoint.io"
//            val url = if (baseUrl != null) {
//                "$baseUrl/0dfb3f9001090cec4eee"
//            } else {
//                "/0dfb3f9001090cec4eee"
//            }
//            val result = retrofit
//                .create(LabDashboardApi::class.java)
//                .dashboard(url)
//            Response.Success(result)
//        }  catch (ex: Exception) {
//            Response.Error(message = ex.message)
//        } finally {
//            Response.Loading<labReportsDashboardResponse>(state = false)
//        }
//    }
//    override suspend fun getMenus(): Response<MenuItems> {
//        return try {
//            Response.Success(myApiList.getMenu())
//        } catch (ex: Exception) {
//            Response.Error(message = ex.message)
//        }
//    }
    override suspend fun getVendorCredentials(): Response<VendorCredentialsResponse> {
        return try{
            Response.Loading<VendorCredentials>(state = true)
            val baseUrl = preference.baseUrl()
            val url = "06c86d3c3a70644628af"
            Log.d("VendorCredentialsURL", "Vendor Credentials URL: $url")
            val result = httpClient
                .create(LabDashboardApi::class.java)
                .getVendorCredentials(url)
            Response.Success(result)
        }catch (ex: Exception){
            Response.Error(message = ex.message)
        }finally {
            Response.Loading<VendorCredentials>(state = false)
        }


    }
}

//https://api.npoint.io/0dfb3f9001090cec4eee
//https://api.npoint.io/0dfb3f9001090cec4eee
//https://api.npoint.io/0dfb3f9001090cec4eee
//https://api.npoint.io/0dfb3f9001090cec4eee