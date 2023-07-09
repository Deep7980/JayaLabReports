package com.jaya.app.labreports.core.domain.usecases

import android.util.Log
import com.jaya.app.labreports.core.common.constants.DataEntry
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.MyQuotesRepository
import com.jaya.app.labreports.core.domain.repositories.UpdateRepository
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.core.utilities.handleFailedResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUseCase @Inject constructor(
    private val repository: UpdateRepository,
    private val appPreference: AppPreference
) {

    fun getAllProducts(id:String) = flow{
        emit(DataEntry(EntryType.LOADING, true))
        val response = repository.getItemDetails(id)
        emit(DataEntry(EntryType.LOADING, false))
        when (response){
            is Response.Success ->{
                emit(DataEntry(EntryType.LOADING, false))
                //Log.d("Products", "getAllProducts: ${response.data?.products}")
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(DataEntry(EntryType.QUOTATIONS, products))
                            Log.d("getSpecificProduct", "getSpecificProduct: $products")
                        }

                        false -> {
                            emit(DataEntry(EntryType.BACKEND_ERROR, message))
                        }
                    }
                }
            }
            is Response.Loading -> {}
            is Response.Error -> handleFailedResponse(response)

        }
    }

    fun getMaterials() = flow{
        emit(DataEntry(EntryType.LOADING,true))
        val response = repository.getMaterials()
        when(response){
            is Response.Success ->{
                emit(DataEntry(EntryType.LOADING,false))
                Log.d("Testing", "MaterialPresent ?= : ${response.data?.materials?.isPresent}")
                response.data?.apply {
                    when(status){
                        true ->{
                            emit(DataEntry(EntryType.MATERIALS, materials))
                            //emit(DataEntry(EntryType.QUOTATIONS))
                            Log.d("getSpecificProduct", "getSpecificProduct: ${materials.materialList.toList()}")
                        }

                        false->{
                            emit(DataEntry(EntryType.BACKEND_ERROR, message))
                        }
                    }
                }
            }
            is Response.Loading -> {}
            is Response.Error -> handleFailedResponse(response)
        }
    }
}