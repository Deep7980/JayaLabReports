package com.jaya.app.labreports.core.utilities

import com.jaya.app.labreports.core.domain.entities.VendorCredentials

interface AppPreference {

    suspend fun setBaseUrl(url: String, changeImmediate: String)
    suspend fun baseUrl(): String?
    suspend fun login(): Boolean

    suspend fun credentials(): VendorCredentials?

    companion object {
        const val UPDATE_LIMIT = "update_limit"
        const val TOKEN = "user_token"
        const val USER = "user_id"
        const val BASE_URL = "base_url"
        const val VENDOR_CREDS = "vendor_creds"
    }
}