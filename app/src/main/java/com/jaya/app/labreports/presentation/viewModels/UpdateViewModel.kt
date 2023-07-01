package com.jaya.app.labreports.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.domain.entities.ItemIdToUpdate
import com.jaya.app.labreports.core.domain.entities.Products
import com.jaya.app.labreports.core.domain.usecases.UpdateUseCase
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.presentation.ui.navigation.toDestination
import com.jaya.app.labreports.utilities.castListToRequiredTypes
import com.jaya.app.labreports.utilities.castValueToRequiredTypes
import com.jaya.app.labreports.utilities.parent_child_relationship.ChildViewModel
import com.jaya.app.labreports.utilities.parent_child_relationship.ParentOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val useCase: UpdateUseCase,
    private val navigator: RouteNavigator
):ViewModel(),RouteNavigator by navigator {

    var notifier = mutableStateOf("")
    private val _productsList = MutableStateFlow(emptyList<Products>())
    val product = _productsList.asStateFlow()

    var productName = mutableStateOf("")
    var productReceiveDate= mutableStateOf("")
    var productQuantity = mutableStateOf("")

    private var isNavigated = false
    fun NavigateBack(){
        if(!isNavigated){
            isNavigated = true
            navigator.navigateToRoute(
                destination = Destination.Dashboard,
                popToRoute = Destination.Updates,
                singleTop = true,
                inclusive = true

            )
        }
    }
//    init {
//        getAllProductsList()
//    }

//    override fun listenParentOrders(orders: StateFlow<ParentOrder<Any>?>) {
//        orders
//            .filterNot {
//                if (it?.order is Navigation) {
//                    val dest = (it.order as Navigation).destination.toDestination()
//                    return@filterNot dest == Destination.Updates
//                }
//                false
//            }
//            .onEach { order ->
//                Log.d("TESTING", "listenOrders: UpdateViewModel")
//                if (order?.order is Navigation) {
//                    navigator.popAndNavigate(
//                        destination = (order.order as Navigation).destination.toDestination(),
//                        singleTop = order.order.singleTop,
//                        inclusive = order.order.popUpto
//                    )
//                }
//
//                if (order?.order is String) {
//                    order.order.castValueToRequiredTypes<String>()?.apply {
//                        notifier.value = this
//                    }
//                }
//            }
//            .launchIn(viewModelScope)
//    }

    fun NavigateToReportSubmit(){
        navigator.popAndNavigate(
            destination = Destination.Dashboard,
            singleTop = true,
            inclusive = true
        )
    }

    fun getSpecificProductsList(id:String){
        useCase.getAllProducts(id)
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EntryType.QUOTATIONS -> {
                        it.data?.castValueToRequiredTypes<Products>()?.let { item->
                            item.apply {
                                productName.value=item.name
                                productReceiveDate.value=item.receivedOn
                                productQuantity.value=item.Quantity
                                Log.d("SpecificProdName", "SpecificProdName: ${productName.value}")
                            }
//                            _productsList.update { products }
//                            Log.d("ProductsList", "getAllProductsList: $products")
//                            Log.d("ProdUIList", "ProdUIList: ${product.value}")
                        }
                    }
                    EntryType.NETWORK_ERROR -> {
                        it.data?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }
                    EntryType.LOADING -> {
                        it.data?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {

                            }
                        }
                    }

//                    EntryType.LOADING -> it.data?.castValueToRequiredTypes<Boolean>()?.let {
//                        quotationsLoading = it
//                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)

    }
}