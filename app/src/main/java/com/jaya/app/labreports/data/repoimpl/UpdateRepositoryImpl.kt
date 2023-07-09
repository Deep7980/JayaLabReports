package com.jaya.app.labreports.data.repoimpl

import android.util.Log
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.entities.Materials
import com.jaya.app.labreports.core.domain.repositories.UpdateRepository
import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.core.model.MaterialDetailResponse
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.data.source.remote.MyQuotesApi
import com.jaya.app.labreports.data.source.remote.UpdateProductsApi
import retrofit2.Retrofit
import javax.inject.Inject

class UpdateRepositoryImpl @Inject constructor(
    private val httpClient: Retrofit,
    private val preference: AppPreference
):UpdateRepository{


    override suspend fun getItemDetails(id: String): Response<LabQuotationsResponse> {
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
                .create(UpdateProductsApi::class.java)
                .getItemDetails(url,id)
            Response.Success(result)
        } catch (ex: Exception) {
            Response.Error(message = ex.message)
        } finally {
            Response.Loading<LabQuotationsResponse>(state = false)
        }
    }

    override suspend fun getMaterials(): Response<MaterialDetailResponse> {
        return try{
            Response.Loading<MaterialDetailResponse>(state = true)
            val baseUrl = preference.baseUrl()
            val url = "e6bb9fee10e7ce9b8ab6"
            Log.d("MaterialsApiURL", "getMaterials: $url")
            val result = httpClient
                .create(UpdateProductsApi::class.java)
                .getMaterialDetails(url)
            Response.Success(result)
        }catch (ex: Exception){
            Response.Error(message = ex.message)
        } finally {
            Response.Loading<LabQuotationsResponse>(state = false)
        }
    }
}