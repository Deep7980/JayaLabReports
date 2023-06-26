package com.jaya.app.labreports.data.repoimpl

import android.util.Log
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.SplashRepository
import com.jaya.app.labreports.core.model.AppMaintenanceResponse
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.data.source.remote.AppMaintenanceApi
import retrofit2.Retrofit
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    //private val httpClient: Retrofit,
    private val appPref: AppPreference

):SplashRepository {

//    override suspend fun appMaintenance(currentAppVersion: Int): Response<AppMaintenanceResponse> {
//        return try {
//            Response.Loading<AppMaintenanceResponse>(state = true)
//            val base = appPref.baseUrl()
//
//            val url = if (base != null) {
//                "$base/app/vendor/appMaintenanace"
//            } else {
//                "/app/vendor/appMaintenanace"
//            }
//            val result = httpClient.create(AppMaintenanceApi::class.java)
//                .maintainApp(
//                    url,
//                    currentAppVersion
//                )
//
//            Response.Success(result)
//        } catch (ex: Exception) {
//            Log.d("TESTING", "${ex.message}")
//            Response.Error(message = ex.message)
//        } finally {
//            Response.Loading<AppMaintenanceResponse>(state = false)
//        }
//    }
}