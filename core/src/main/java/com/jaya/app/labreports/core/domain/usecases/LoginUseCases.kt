package com.jaya.app.labreports.core.domain.usecases

import android.util.Log
import com.jaya.app.labreports.core.common.constants.DataEntry
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.enums.Page
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.LoginRepository
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.core.utilities.handleFailedResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCases @Inject constructor(
    private val repository: LoginRepository,
    private val preference: AppPreference
) {

    fun sendCode(mobileNumber: String) = flow {
        emit(DataEntry(EntryType.LOADING, true))
        when (val response = repository.sendCode(mobileNumber)) {
            is Response.Success -> {
                emit(DataEntry(EntryType.LOADING, false))
                Log.d("CodeSendedToMobile", "sendCode: ${response.message}")
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(
                                DataEntry(EntryType.NAVIGATE,
                                    Navigation(
                                        destination = Page.DASHBOARD,
                                        popDestination = Page.LOGIN,
                                        popUpto = true
                                    )
                                )
                            )
                            emit(DataEntry(EntryType.INFORM, message))
                        }

                        false -> handleFailedResponse(response)
                    }
                }
            }

            is Response.Loading -> {}
            is Response.Error -> throw IllegalArgumentException(response.message)
        }
    }
//
//    fun verifyCode(mobileNumber: String, code: String) = flow {
//        emit(DataEntry(EntryType.LOADING, true))
//        when (val response = repository.verifyCode(mobileNumber, code)) {
//            is Response.Success -> {
//                emit(DataEntry(EntryType.LOADING, false))
//                response.data?.apply {
//                    when (status) {
//                        true -> {
//                            vendorCreds.apply {
//                                if (isVerified) {
////                                    preference.setUserId(id = id)
////                                    preference.setUserToken(token = token)
////                                    preference.setVendorCredentials(vendorCreds)
//                                    emit(
//                                        DataEntry(
//                                            EntryType.NAVIGATE,
//                                            Navigation(Page.DASHBOARD, Page.LOGIN, popUpto = true)
//                                        )
//                                    )
//                                }
//                            }
//                            emit(DataEntry(EntryType.INFORM, message))
//                        }
//
//                        false -> handleFailedResponse(response)
//                    }
//                }
//            }
//
//            is Response.Loading -> {}
//            is Response.Error -> throw IllegalArgumentException(response.message)
//        }
//    }

    fun decideNavigation() = flow {
        emit(
            DataEntry(
                EntryType.NAVIGATE,
                Navigation(
                    destination = Page.DASHBOARD,
                    popDestination = Page.LOGIN,
                    popUpto = true
                )
            )
        )
    }


}