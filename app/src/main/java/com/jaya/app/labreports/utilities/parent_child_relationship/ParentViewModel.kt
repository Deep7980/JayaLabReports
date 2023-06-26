package com.jaya.app.labreports.utilities.parent_child_relationship

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class ParentViewModel: ViewModel() {
    private val _parentOrders = MutableStateFlow<ParentOrder<Any>?>(null)
    val parentOrders: StateFlow<ParentOrder<Any>?> = _parentOrders.asStateFlow()
    abstract fun listenChildRequests(requests: StateFlow<ChildRequest<Any>?>)
    fun sentOrderToChild(order: ParentOrder<Any>) {
        _parentOrders.update { order }
    }
    fun onClear() {
        _parentOrders.update { null }
    }
}