package com.jaya.app.labreports.utilities.parent_child_relationship

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class ChildViewModel:ViewModel() {
    abstract fun listenParentOrders(orders: StateFlow<ParentOrder<Any>?>)

    private val _childRequestFlow = MutableStateFlow<ChildRequest<Any>?>(null)
    val childRequestFlow: StateFlow<ChildRequest<Any>?> = _childRequestFlow.asStateFlow()

    fun sentRequestToParent(request: ChildRequest<Any>) {
        _childRequestFlow.update { request }
    }

    fun onClear() {
        _childRequestFlow.update { null }
    }
}