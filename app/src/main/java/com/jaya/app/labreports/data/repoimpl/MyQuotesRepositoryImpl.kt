package com.jaya.app.labreports.data.repoimpl

import android.util.Log
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.MyQuotesRepository
import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.data.source.remote.MyQuotesApi
import retrofit2.Retrofit
import javax.inject.Inject


//https://api.npoint.io/0dfb3f9001090cec4eee
class MyQuotesRepositoryImpl @Inject constructor(
    private val httpClient: Retrofit,
    private val preference: AppPreference
) : MyQuotesRepository {

    //    override suspend fun myQuotes(variance: QuotationVariance): Response<LabQuotationsResponse> {
//        return try {
//            Response.Loading<LabQuotationsResponse>(state = true)
//            val baseUrl = "https://api.npoint.io"
//            val url = if (baseUrl != null) {
//                "$baseUrl/0dfb3f9001090cec4eee"
//            } else {
//                "/0dfb3f9001090cec4eee"
//            }
//            Log.d("URL", "myQuotes: $url")
//            val result = httpClient
//                .create(MyQuotesApi::class.java)
//                .myQuotes(url, variance.variance)
//            Response.Success(result)
//        } catch (ex: Exception) {
//            Response.Error(message = ex.message)
//        } finally {
//            Response.Loading<LabQuotationsResponse>(state = false)
//        }
//    }
    override suspend fun myQuotes(): Response<LabQuotationsResponse> {
        return try {
            Response.Loading<LabQuotationsResponse>(state = true)
            val baseUrl = preference.baseUrl()
            /*val url = if (baseUrl != null) {
                "$baseUrl/vendor/$userId/biddings"
            } else {
                "/vendor/$userId/biddings"
            }*/
            //val  url = "d2046e5c99829a0ed63c"
            val url = "6196bd728a7a4ca83d15"
            Log.d("URL", "ProductsListURL = : $url")
            val result = httpClient
                .create(MyQuotesApi::class.java)
                .myQuotes(url)
            Response.Success(result)
        } catch (ex: Exception) {
            Response.Error(message = ex.message)
        } finally {
            Response.Loading<LabQuotationsResponse>(state = false)
        }
    }

//    override suspend fun myQuotes(): Response<LabQuotationsResponse> {
//        return try {
//            Response.Success(myApiList.getProducts())
//        } catch (ex: Exception) {
//            Response.Error(message = ex.message)
//        }
//    }
}