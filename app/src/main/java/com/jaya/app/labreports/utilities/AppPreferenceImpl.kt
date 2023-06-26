package com.jaya.app.labreports.utilities

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jaya.app.labreports.core.utilities.AppPreference
import javax.inject.Inject

class AppPreferenceImpl @Inject constructor(
    private val instance: DataStore<Preferences>
):AppPreference {
    override suspend fun setBaseUrl(url: String, changeImmediate: String) {
        TODO("Not yet implemented")
    }

    override suspend fun baseUrl(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun login(): Boolean {
        TODO("Not yet implemented")
    }
}