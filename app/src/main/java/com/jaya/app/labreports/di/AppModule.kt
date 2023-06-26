package com.jaya.app.labreports.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jaya.app.labreports.core.common.enums.MetarFields
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.utilities.AppPreferenceImpl
import com.jaya.app.labreports.utilities.Metar
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGlobalCoroutineScope(): CoroutineScope =
        CoroutineScope(context = Dispatchers.Main + SupervisorJob())

    @Provides
    @Singleton
    fun provideAppPreference(
        @ApplicationContext context: Context,
    ): AppPreference {
        return AppPreferenceImpl(
            preferencesDataStore(name = "jaya-labreports").getValue(
                context, DataStore<Preferences>::javaClass
            )
        )
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(enable = true)
                .build()
        ).build()

        return Retrofit.Builder()
            .baseUrl(
                HttpUrl
                .Builder()
                .scheme("https").host(Metar[MetarFields.BaseUrl()]).build())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}