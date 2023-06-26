package com.jaya.app.labreports.core.common.sealed

sealed class Response<T>(
    val data: T? = null,
    val message: String? = null,
    val progress: Int? = null,
    val state: Boolean = false,
) {
    class Success<T>(data: T) : Response<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : Response<T>(data, message)
    class Loading<T>(progress: Int? = null, state: Boolean = false) :
        Response<T>(progress = progress, state = state)
}