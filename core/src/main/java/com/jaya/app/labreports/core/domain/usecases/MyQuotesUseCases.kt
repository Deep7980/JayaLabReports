package com.jaya.app.labreports.core.domain.usecases

import android.util.Log
import com.jaya.app.labreports.core.common.constants.DataEntry
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.enums.QuotationVariance
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.MyQuotesRepository
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.core.utilities.handleFailedResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyQuotesUseCases @Inject constructor(
    private val repository: MyQuotesRepository,
    private val appPreference: AppPreference
) {

    fun myQuotes(selectedVariance: QuotationVariance) = flow {
        emit(DataEntry(EntryType.LOADING, true))
        when (val response = repository.myQuotes()) {
            is Response.Success -> {
                emit(DataEntry(EntryType.LOADING, false))
                response.data?.apply {
                    Log.d("ResponseData", "myQuotes Data = : ${response.data.products}")
                    when (status) {
                        true -> {
                            emit(DataEntry(EntryType.QUOTATIONS, products))
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
}