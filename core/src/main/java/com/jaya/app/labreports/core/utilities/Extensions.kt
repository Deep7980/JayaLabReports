package com.jaya.app.labreports.core.utilities

import com.jaya.app.labreports.core.common.constants.DataEntry
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.sealed.Response
import kotlinx.coroutines.flow.FlowCollector

suspend inline fun <reified R> FlowCollector<DataEntry>.handleFailedResponse(
    response: Response<R>,
    emitType: EntryType = EntryType.NETWORK_ERROR
) {
    emit(DataEntry(emitType, response.message))
}