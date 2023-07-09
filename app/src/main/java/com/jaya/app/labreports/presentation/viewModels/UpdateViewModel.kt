package com.jaya.app.labreports.presentation.viewModels

import android.graphics.pdf.PdfDocument.Page
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.domain.entities.ItemIdToUpdate
import com.jaya.app.labreports.core.domain.entities.Materials
import com.jaya.app.labreports.core.domain.entities.Products
import com.jaya.app.labreports.core.domain.usecases.UpdateUseCase
import com.jaya.app.labreports.core.model.MaterialDetailResponse
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.Graph
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.presentation.ui.navigation.toDestination
import com.jaya.app.labreports.utilities.castListToRequiredTypes
import com.jaya.app.labreports.utilities.castValueToRequiredTypes
import com.jaya.app.labreports.utilities.parent_child_relationship.ChildViewModel
import com.jaya.app.labreports.utilities.parent_child_relationship.ParentOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val useCase: UpdateUseCase,
    private val navigator: RouteNavigator
):ViewModel(),RouteNavigator by navigator {

    var notifier = mutableStateOf("")
    private val _productsList = MutableStateFlow(emptyList<Products>())
    val product = _productsList.asStateFlow()
    var parameterValue = mutableStateOf("13.0")
    var productName = mutableStateOf("")
    var productReceiveDate= mutableStateOf("")
    var productQuantity = mutableStateOf("")

    private val _materialList = MutableStateFlow(emptyList<String>())
    val materialList = _materialList.asStateFlow()

    private val _materials = MutableStateFlow(emptyList<Materials>())
    val materials = _materialList.asStateFlow()

    private val materialResponse = MutableStateFlow("")

    private val _materialValue =  MutableStateFlow(emptyList<Float>())
    val materialValue = _materialValue.asStateFlow()
    val _materialDesc = MutableStateFlow(emptyList<String>())
    val materialDesc = _materialDesc.asStateFlow()
    var quotationsLoading by mutableStateOf(false)
        private set

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
    fun getMaterialDesc(){
        val list = arrayListOf<String>("abc","djdjj","djdjd","djdjd","djdj")
        _materialDesc.update { list }
    }

    fun onBackPress(){
        navigator.navigateToRoute(
            destination = Destination.Dashboard,
            popToRoute = Destination.Updates,
            singleTop = true,
            inclusive = true

        )
    }
    init {
        getMaterialDetails()
        getMaterialDesc()
        viewModelScope.launch {
            delay(3000L)
            quotationsLoading=false
        }
    }

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


    fun getMaterialDetails(){
        useCase.getMaterials()
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EntryType.MATERIALS ->{
                        it.data?.castValueToRequiredTypes<Materials>()?.let { materials ->
                            materials.apply {
                                 _materialList.update { materials.materialList }
                                _materialValue.update { materials.value }
                                productName.value=materials.name
                                productReceiveDate.value=materials.receivedOn
                                productQuantity.value=materials.Quantity
                            }
                            Log.d("MatreialName", "Material Name: ${materials.name}")
                            Log.d("MatreialQuantity", "Material Quantity: ${materials.Quantity}")
                            Log.d("MatreialList", "Material List: ${materials.materialList.toList()}")
                            Log.d("MatreialValue", "Material value: ${materials.value.toList()}")

                        }
                    }
                    EntryType.BACKEND_ERROR -> {
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