package com.jaya.app.labreports.presentation.viewModels

import android.widget.Toast
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.labreports.app.JayaLabReports
import com.jaya.app.labreports.core.common.constants.DataEntry
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.enums.UiData
import com.jaya.app.labreports.core.domain.usecases.LoginUseCases
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.presentation.ui.navigation.toDestination
import com.jaya.app.labreports.presentation.viewstates.SavableMutableState
import com.jaya.app.labreports.utilities.castValueToRequiredTypes
import com.jaya.app.labreports.utilities.handleErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    private val useCases: LoginUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel(), RouteNavigator by navigator {

    val mobileNumber = SavableMutableState(
        key = UiData.MobileNumber,
        savedStateHandle = savedStateHandle,
        initialData = ""
    )

    val userCode = SavableMutableState(
        key = UiData.Code,
        savedStateHandle = savedStateHandle,
        initialData = ""
    )

    val getCodeBtnState = SavableMutableState(
        key = UiData.BtnState,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    var sendLoading by mutableStateOf(false)
        private set


    val sendStatus = SavableMutableState(
        key = UiData.VerificationStatus,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    val codeBtnState = SavableMutableState(
        key = UiData.CodeBtnState,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    var codeVerifyLoading by mutableStateOf(false)
        private set

    val notifier = mutableStateOf("")

    fun onNumberChange(number: String) {
        mobileNumber.setValue(
            number
        )
        getCodeBtnState.setValue(derivedStateOf {
            number.length == 10
        }.value)
    }

    fun onChangeCode(code: String) {
        userCode.setValue(
            code
        )
        codeBtnState.setValue(
            derivedStateOf {
                code.length == 4
            }.value
        )
    }

    fun editNumber() {
        sendStatus.setValue(false)
    }

    fun sendCodeToNumber() {
        useCases
            .sendCode(mobileNumber.value)
            .catch { emit(DataEntry(EntryType.LOADING, true)) }
            .onEach {
                when (it.type) {
                    EntryType.LOADING -> {
                        it.data?.castValueToRequiredTypes<Boolean>()?.apply {
                            sendLoading = this
                        }
                    }

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

                    EntryType.INFORM -> {
                        it.data?.castValueToRequiredTypes<String>()?.apply {
                            notifier.value = this
                        }
                    }

                    else -> {
                        it.handleErrors()?.apply {
                            notifier.value = this
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }
//
//    fun verifyCode() {
//        useCases.verifyCode(
//            mobileNumber = mobileNumber.value,
//            code = userCode.value
//        )
//            .catch { emit(DataEntry(EntryType.LOADING, true)) }
//            .onEach {
//                when (it.type) {
//                    EntryType.LOADING -> {
//                        it.data?.castValueToRequiredTypes<Boolean>()?.apply {
//                            codeVerifyLoading = this
//                        }
//                    }
//
//                    EntryType.NAVIGATE -> {
//                        it.data?.castValueToRequiredTypes<Navigation>()?.apply {
//                            navigator.navigateToRoute(
//                                destination = destination.toDestination(),
//                                popToRoute = popDestination.toDestination(),
//                                inclusive = popUpto,
//                                singleTop = singleTop
//                            )
//                        }
//                    }
//
//                    EntryType.INFORM -> {
//                        it.data?.castValueToRequiredTypes<String>()?.apply {
//                            notifier.value = this
//                        }
//                    }
//
//                    else -> {
//                        it.handleErrors()?.apply {
//                            notifier.value = this
//                        }
//                    }
//                }
//            }.launchIn(viewModelScope)
//    }

    fun navigateFurther() {
        useCases.decideNavigation().onEach {
            when (it.type) {
                EntryType.NAVIGATE -> {
                    it.data?.castValueToRequiredTypes<Navigation>()?.apply {
                        navigator.navigateToRoute(
                            destination = destination.toDestination(),
                            popToRoute = popDestination.toDestination(),
                            inclusive = true,
                            singleTop = true
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}