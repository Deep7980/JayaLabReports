package com.jaya.app.labreports.core.domain.usecases

import android.util.Log
import com.jaya.app.labreports.core.common.constants.DataEntry
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.enums.Page
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.DashboardRepository
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.core.utilities.handleFailedResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DashboardUseCases @Inject constructor(
    private val repository: DashboardRepository,
    private val preference: AppPreference
) {
    fun logout() = flow {
            emit(
                DataEntry(
                    EntryType.NAVIGATE,
                    Navigation(
                        destination = Page.LOGIN,
                        popDestination = Page.NONE,
                        popUpto = true,
                        singleTop = true
                    )
                )
            )
    }
    fun userDetails() = flow {
        val creds = preference.credentials()
        if(creds != null) {
            emit(DataEntry(EntryType.VENDOR_CREDS, creds))
        } else {
            emit(DataEntry(EntryType.CREDS_ERROR, null))
        }
    }

//    fun getMenuList() = flow{
//        when(val response = repository.getMenus()){
//            is Response.Success -> {
//                Log.d("TESTING", "getMenuList: ${response.data?.first}")
//                response.data?.apply {
//                    when (status) {
//                        true -> {
//                            emit(DataEntry(EntryType.QUOTATIONS, first))
//                            emit(DataEntry(EntryType.QUOTATIONS, second))
//                        }
//
//                        false -> {
//                            emit(DataEntry(EntryType.BACKEND_ERROR, status))
//                        }
//                    }
//                }
//            }
//            is Response.Loading -> {}
//            is Response.Error -> handleFailedResponse(response)
//
//        }
//    }
}