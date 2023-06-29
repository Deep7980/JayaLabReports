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



    private val Context.dataStore by preferencesDataStore("jayaPackaging")


//    private fun <T> provideApi(klass: Class<T>): T {
//        val okHttpClient = OkHttpClient.Builder().addInterceptor(
//            ChuckerInterceptor.Builder(JayaLabReports.INSTANCE!!.applicationContext)
//                .collector(ChuckerCollector(JayaLabReports.INSTANCE!!.applicationContext))
//                .maxContentLength(250000L)
//                .redactHeaders(emptySet())
//                .alwaysReadResponseBody(false)
//                .build()
//        ).build()
//
//        return Retrofit.Builder()
//            .baseUrl(Metar[Constants.BASE_URL])
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(klass)
//    }

////////-----------------------------------------------------------------------------------------------------

//        private fun <T> provideApi2(klass: Class<T>): T {
//            val okHttpClient = OkHttpClient.Builder().addInterceptor(
//                ChuckerInterceptor.Builder(JayaPackagingApp.app!!.applicationContext)
//                    .collector(ChuckerCollector(JayaPackagingApp.app!!.applicationContext))
//                    .maxContentLength(250000L)
//                    .redactHeaders(emptySet())
//                    .alwaysReadResponseBody(false)
//                    .build()
//            ).build()
//
//            return Retrofit.Builder()
//                .baseUrl("https://api.npoint.io/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build()
//                .create(klass)
//        }


//    @Singleton
//    @Provides
//    fun provideMyApiList(): MyApiList = provideApi(MyApiList::class.java)


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

//    @Singleton
//    @Provides
//    fun provideMyApiList(): MyQuotesApi = provideHttpClient(MyQuotesApi::class.java)


}