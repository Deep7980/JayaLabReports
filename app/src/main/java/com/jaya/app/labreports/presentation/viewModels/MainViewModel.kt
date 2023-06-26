package com.jaya.app.labreports.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.utilities.AppConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    val connectivity: AppConnectivity
): ViewModel(), RouteNavigator by navigator {

    fun appNavigator() = navigator
}