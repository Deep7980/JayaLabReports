package com.jaya.app.labreports.core.domain.usecases

import com.jaya.app.labreports.core.common.constants.DataEntry
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.enums.Page
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.SplashRepository
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.core.utilities.AppPreference
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashUseCases @Inject constructor(
    private val repository: SplashRepository,
    private val prefs: AppPreference
) {

//    fun appMaintenanceApi(currentAppVersion: Int) = flow {
//        emit(DataEntry(EntryType.LOADING, true))
//        when (val response = repository.appMaintenance(currentAppVersion)) {
//            is Response.Success -> {
//                emit(DataEntry(EntryType.LOADING, false))
//                response.data?.apply {
//                    when (status) {
//                        true -> {
//                            prefs.setBaseUrl(domain.base, domain.changeImmediate)
//                            if (version.isUpdateAvailable) {
//                                emit(DataEntry(EntryType.VERSION_UPDATE, version))
//                            } else {
//                                emit(DataEntry(EntryType.CALL_NEXT_API, null))
//                            }
//                        }
//
//                        false -> handleFailedResponse(response = response)
//                    }
//                }
//            }
//
//            is Response.Loading -> {
//
//            }
//
//            is Response.Error -> {
//                throw IllegalArgumentException(response.message)
//            }
//        }
//
//    }

    fun decideNavigation() = flow {

//        if (!prefs.login()) {
            emit(
                DataEntry(
                    EntryType.NAVIGATE, Navigation(
                        destination = Page.UPDATE,
                        popDestination = Page.SPLASH,
                        popUpto = true
                    )
                )
            )
//        } else {
//            emit(
//                DataEntry(
//                    EntryType.NAVIGATE, Navigation(
//                        destination = Page.DASHBOARD,
//                        popDestination = Page.SPLASH,
//                        popUpto = true
//                    )
//                )
//            )
//        }

    }
}