package com.jaya.app.labreports.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.jaya.app.labreports.core.model.Navigation
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.presentation.ui.navigation.toDestination
import com.jaya.app.labreports.utilities.castValueToRequiredTypes
import com.jaya.app.labreports.utilities.parent_child_relationship.ChildViewModel
import com.jaya.app.labreports.utilities.parent_child_relationship.ParentOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val navigator: RouteNavigator
):ChildViewModel(),RouteNavigator by navigator {

    var notifier = mutableStateOf("")

    override fun listenParentOrders(orders: StateFlow<ParentOrder<Any>?>) {
        orders
            .filterNot {
                if (it?.order is Navigation) {
                    val dest = (it.order as Navigation).destination.toDestination()
                    return@filterNot dest == Destination.Updates
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
}