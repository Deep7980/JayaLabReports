package com.jaya.app.labreports.utilities

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jaya.app.labreports.core.domain.entities.Domain
import com.jaya.app.labreports.core.domain.entities.VendorCredentials
import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.core.utilities.AppPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppPreferenceImpl @Inject constructor(
    private val instance: DataStore<Preferences>
):AppPreference {
    override suspend fun setBaseUrl(url: String, changeImmediate: String) {
        if (changeImmediate == Domain.CHANGE) {
            instance.edit {
                it[stringPreferencesKey(AppPreference.BASE_URL)] = url
            }
        } else {
            instance.edit {
                it[stringPreferencesKey(AppPreference.BASE_URL)] = ""
            }
        }
    }

    override suspend fun baseUrl(): String? {
        val url = instance.data.map {
            it[stringPreferencesKey(AppPreference.BASE_URL)]
        }.first()

        return if (!url.isNullOrEmpty()) "https://$url" else null
    }

    override suspend fun login(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun credentials(): VendorCredentials? {
        TODO("Not yet implemented")
    }

    override suspend fun myQuotes(): LabQuotationsResponse {
        TODO("Not yet implemented")
    }
}