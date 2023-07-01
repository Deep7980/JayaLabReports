package com.jaya.app.labreports.presentation.viewModels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.enums.QuotationVariance
import com.jaya.app.labreports.core.common.enums.UiData
import com.jaya.app.labreports.core.domain.entities.ItemIdToUpdate
import com.jaya.app.labreports.core.domain.entities.Products
import com.jaya.app.labreports.core.domain.usecases.MyQuotesUseCases
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.presentation.ui.navigation.toDestination
import com.jaya.app.labreports.presentation.viewstates.QuoteVarianceItem
import com.jaya.app.labreports.presentation.viewstates.SavableMutableState
import com.jaya.app.labreports.presentation.viewstates.VendorQuotationItem
import com.jaya.app.labreports.utilities.AppConnectivity
import com.jaya.app.labreports.utilities.castListToRequiredTypes
import com.jaya.app.labreports.utilities.castValueToRequiredTypes
import com.jaya.app.labreports.utilities.handleErrors
import com.jaya.app.labreports.utilities.parent_child_relationship.ChildRequest
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
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule


@HiltViewModel
class MyQuotesViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    private val connectivity: AppConnectivity,
    private val useCases: MyQuotesUseCases
): ChildViewModel(), RouteNavigator by navigator {

    //val dataLoading = SavableMutableState(UiData.StateApiLoading,savedStateHandle,false)
    private val _productsList = MutableStateFlow(emptyList<Products>())
    val ProductsList = _productsList.asStateFlow()
    val connectivityStatus = connectivity.connectivityStatusFlow
    var status = mutableStateOf("")


    init {
        getAllProductsList()
    }


    fun updateScreen(id:String){
        ItemIdToUpdate.IdToUpdate=id
        Log.d("SpecificItemID", "Specific Item ID: $id")
//        navigator.popAndNavigate(
//            destination = Destination.Updates,
//            singleTop = false,
//            inclusive = false
//        )
        sentRequestToParent(ChildRequest(Destination.Updates))
//        navigator.navigateToRoute(
//            destination = Destination.Updates,
//            popToRoute = Destination.MyQuotes,
//            singleTop = false,
//            inclusive = false
//        )
    }

    override fun listenParentOrders(orders: StateFlow<ParentOrder<Any>?>) {
        orders
            .filterNot {
                if (it?.order is Navigation) {
                    val dest = (it.order as Navigation).destination.toDestination()
                    return@filterNot dest == Destination.MyQuotes
                }
                false
            }
            .onEach { order ->
                Log.d("TESTING", "listenOrders: MyQuotesViewModel")
                if (order?.order is Navigation) {
                    navigator.popAndNavigate(
                        destination = (order.order as Navigation).destination.toDestination(),
                        singleTop = order.order.singleTop,
                        inclusive = order.order.popUpto
                    )
                }

                if (order?.order is String) {
                    order.order.castValueToRequiredTypes<String>()?.apply {
                        notifier.value = this
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private val _quotations = MutableStateFlow<List<VendorQuotationItem>?>(null)
    val quotations = _quotations.asStateFlow()

    var selectedVariance by mutableStateOf(QuotationVariance.NewEntry)
        private set

    private val _quoteVariances = MutableStateFlow(QuotationVariance.values().map {
        if (it == QuotationVariance.NewEntry) {
            QuoteVarianceItem(variance = it, selected = true)
        } else {
            QuoteVarianceItem(variance = it)
        }
    })

    var quotationsLoading by mutableStateOf(false)
        private set
    val quoteVariances = _quoteVariances.asStateFlow()
    var notifier = mutableStateOf("")


//    fun quotations(variance: QuotationVariance) {
//        useCases.myQuotes(variance).flowOn(Dispatchers.IO).onEach {
//            when (it.type) {
//                EntryType.QUOTATIONS -> it.data?.castListToRequiredTypes<Products>()
//                    ?.let { quotes ->
//                        selectedVariance = variance
//                        _quotations.update {
//                            quotes.map { item ->
//                                VendorQuotationItem(
//                                    item = mutableStateOf(item)
//                                )
//                            }
//                        }
//                    }
//
//                EntryType.LOADING -> it.data?.castValueToRequiredTypes<Boolean>()?.let {
//                    quotationsLoading = it
//                }
//
//                else -> {
//                    it.handleErrors()?.let {
//                        notifier.value = it
//                    }
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

    fun getAllProductsList(){
        useCases.getAllProducts()
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EntryType.QUOTATIONS -> {
                        it.data?.castListToRequiredTypes<Products>()?.let { products->
                            _productsList.update { products }
                            Log.d("ProductsList", "getAllProductsList: ${products}")
                            Log.d("ProdUIList", "ProdUIList: ${ProductsList.value}")
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
                                quotationsLoading = it
                            }
                        }
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)

    }

    fun onVarianceClicked(varianceItem: QuoteVarianceItem) {
        _quoteVariances.update {
            it.map {
                if (it == varianceItem) {
                    it.copy(selected = true)
                } else it.copy(selected = false)
            }
        }
    }


}