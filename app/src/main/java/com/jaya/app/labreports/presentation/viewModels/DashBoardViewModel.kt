package com.jaya.app.labreports.presentation.viewModels

import android.app.Application
import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.labreports.app.JayaLabReports
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.sealed.DialogData
import com.jaya.app.labreports.core.domain.entities.Products
import com.jaya.app.labreports.core.domain.entities.VendorCredentials
import com.jaya.app.labreports.core.domain.usecases.DashboardUseCases
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.presentation.ui.navigation.toDestination
import com.jaya.app.labreports.presentation.viewstates.DrawerMenuItem
import com.jaya.app.labreports.presentation.viewstates.DrawerMenus
import com.jaya.app.labreports.utilities.MyDialog
import com.jaya.app.labreports.utilities.castListToRequiredTypes
import com.jaya.app.labreports.utilities.castValueToRequiredTypes
import com.jaya.app.labreports.utilities.parent_child_relationship.ChildRequest
import com.jaya.app.labreports.utilities.parent_child_relationship.ParentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jaya.app.labreports.utilities.shortToast


@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    private val useCases: DashboardUseCases
): ParentViewModel(), RouteNavigator by navigator {

    val dashboardBack = mutableStateOf<MyDialog?>(null)

    init {
        vendorDetails()
    }

    fun onBackDialog(){
        dashboardBack.value = MyDialog(
            data = DialogData(
                title = "Jaya Lab Reports",
                message = "Are you sure you want to exit ?",
                positive = "Yes",
                negative = "No",
            )
        )
        handleDialogEvents()
    }
    private fun handleDialogEvents() {
        dashboardBack.value?.onConfirm = {

        }
        dashboardBack.value?.onDismiss = {
            dashboardBack.value?.setState(MyDialog.Companion.State.DISABLE)
        }
    }
    override fun listenChildRequests(requests: StateFlow<ChildRequest<Any>?>) {
        viewModelScope.launch {
            requests.collectLatest {
                Log.d("TESTING", "listenChildRequests: $it")
                if(it?.data is Destination){
                    it.data.castValueToRequiredTypes<Destination>()?.let {
                        navigator.popAndNavigate(
                            destination = Destination.Updates,
                            //popToRoute = Destination.Dashboard,
                            singleTop = true,
                            inclusive = true

                        )
                    }
                }
            }
        }
    }


    private val _vendorDetails = MutableStateFlow<VendorCredentials?>(null)
    val vendorDetails = _vendorDetails.asStateFlow()

    private val _drawerMenus = MutableStateFlow(DrawerMenus.values().map {
        DrawerMenuItem(menu = it)
    })
    val drawerMenus = _drawerMenus.asStateFlow()

    fun onDrawerMenuClicked(index: Int, selected: DrawerMenuItem) {
        _drawerMenus.update {
            it.map { item ->
                if (item == selected) {
                    item.copy(selected = true)
                } else item.copy(selected = false)
            }
        }
        navigateToMenu(selected.menu)
    }

    private fun navigateToMenu(menu: DrawerMenus) {
        when (menu) {
//            DrawerMenus.AvailableQuotes -> {
//                Toast.makeText(JayaLabReports.INSTANCE?.applicationContext,"NO Available Quotes page.",Toast.LENGTH_SHORT).show()
////                navigator.navigateToRoute(
////                    destination = menu.mDestination,
////                    popToRoute = Destination.NONE,
////                    inclusive = false,
////                    singleTop = false
////                )
//            }

            DrawerMenus.Logout -> {
                useCases.logout().onEach {
                    when (it.type) {
                        EntryType.NAVIGATE -> {
                            it.data?.castValueToRequiredTypes<Navigation>()?.apply {
                                navigator.popAndNavigate(
                                    destination = destination.toDestination(),
                                    singleTop = singleTop,
                                    inclusive = popUpto
                                )
                            }
                        }

                        else -> {}
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun vendorDetails() {
        useCases
            .userDetails()
            .onEach {
                when (it.type) {
                    EntryType.VENDOR_CREDS ->{
                        it.data?.castValueToRequiredTypes<VendorCredentials>()?.let {vendorData->
                            _vendorDetails.update { vendorData }
                            Log.d("vendorDetails", "vendorDetails: ${vendorData.name}")
                        }
                    }


                    EntryType.NETWORK_ERROR -> {

                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

//    private fun getDrawerMenu(){
//        useCases.getMenuList()
//            .flowOn(Dispatchers.IO)
//            .onEach {
//                when(it.type){
//                    EntryType.QUOTATIONS -> {
//                        it.data?.castValueToRequiredTypes<MenuItems>()?.let {
//                            first.value=it.first
//                            second.value=it.second
//                            Log.d("menulist", "menu items: ${first.value} ${second.value}")
//                        }
//                    }
//
//                    else -> {}
//                }
//            }
//    }
}