package com.jaya.app.labreports.presentation.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.domain.usecases.SplashUseCases
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.presentation.ui.navigation.toDestination
import com.jaya.app.labreports.utilities.AppConnectivity
import com.jaya.app.labreports.utilities.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    private val connectivity: AppConnectivity,
    private val useCases: SplashUseCases
):ViewModel(), RouteNavigator by navigator {

    val connectivityStatus = connectivity.connectivityStatusFlow

    val splashAnimation = mutableStateOf(false)

    //val updateApp = mutableStateOf<DialogHandler?>(null)

    val appPlayStoreLink = mutableStateOf<String?>(null)

    init {
        viewModelScope.launch {
            splashAnimation()
            Timer().schedule(5000){
                navigateFurther()
            }
        }
    }

    private suspend fun splashAnimation() {
        splashAnimation.value = true
        delay(500L)
        splashAnimation.value = false
    }

//    private fun appMaintenance() {
//        useCases
//            .appMaintenanceApi(
//                currentAppVersion = 1
//            )
//            .retryWithExponentialBackoff(
//                initialDelay = 500.milliseconds,
//                factor = 2.0,
//                maxAttempt = 5
//            )
//            .catch {
//                emit(DataEntry(EntryType.NETWORK_ERROR, it.message))
//            }
//            .onEach {
//                when (it.type) {
//                    EntryType.VERSION_UPDATE -> {
//                        it.data?.castValueToRequiredTypes<AppVersion>()?.let { version ->
//                            updateApp.value = DialogHandler(
//                                data = DialogData(
//                                    title = R.string.update_app,
//                                    message = version.description,
//                                    positive = R.string.update,
//                                    negative = R.string.skip,
//                                    data = version
//                                )
//                            )
//                            updateApp.value?.consumeEvents(this)
//                        }
//                    }
//                    EntryType.CALL_NEXT_API->{
//                        navigateFurther()
//                    }
//
//                    else -> {
//                        it.handleErrors()?.let {
//
//                        }
//                    }
//                }
//            }.launchIn(viewModelScope)
//    }

    private fun navigateFurther() {
        useCases.decideNavigation().onEach {
            when (it.type) {
                EntryType.NAVIGATE -> {
                    it.data?.castValueToRequiredTypes<Navigation>()?.apply {
                        navigator.navigateToRoute(
                            destination = destination.toDestination(),
                            popToRoute = popDestination.toDestination(),
                            inclusive = popUpto,
                            singleTop = singleTop
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

//    override fun onConfirm(data: Any?) {
//        if (data is AppVersion) {
//            appPlayStoreLink.value = data.link
//        }
//        updateApp.value?.setState(DialogHandler.Companion.State.DISABLE)
//    }
//
//    override fun onDismiss(data: Any?) {
//        updateApp.value?.setState(DialogHandler.Companion.State.DISABLE)
//        navigateFurther()
//    }
}