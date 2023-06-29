package com.jaya.app.labreports.data.repoimpl

import android.util.Log
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.domain.repositories.LoginRepository
import com.jaya.app.labreports.core.model.SendCodeResponse
import com.jaya.app.labreports.core.model.VerifyCodeResponse
import com.jaya.app.labreports.core.utilities.AppPreference
import com.jaya.app.labreports.data.source.remote.SendCodeApi
import com.jaya.app.labreports.data.source.remote.VerifyCodeApi
import retrofit2.Retrofit
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val preference: AppPreference

):LoginRepository{
//    override suspend fun sendCode(mobileNumber: String): Response<SendCodeResponse> {
//        return try {
//            Response.Loading<SendCodeResponse>(state = true)
//            val baseUrl = preference.baseUrl()
//            val url = if (baseUrl != null) {
//                "$baseUrl/app/vendor/otp/sent"
//            } else {
//                "/app/vendor/otp/sent"
//            }
//            val result = httpClient
//                .create(SendCodeApi::class.java)
//                .sendCode(url, mobileNumber)
//            Response.Success(result)
//        } catch (ex: Exception) {
//            Response.Error(message = ex.message)
//        } finally {
//            Response.Loading<SendCodeResponse>(state = false)
//        }
//    }

    //https://api.npoint.io/352dcebdd1fa2ddf949c
//    override suspend fun sendCode(mobileNumber: String): Response<SendCodeResponse> {
//        return try {
//            Response.Loading<SendCodeResponse>(state = true)
//            val baseUrl = "https://api.npoint.io"
//            val url = if (baseUrl != null) {
//                "$baseUrl/352dcebdd1fa2ddf949c"
//
//            } else {
//                "/352dcebdd1fa2ddf949c"
//            }
//            Log.d("URL", "URL = : $url")
//            val result = httpClient
//                .create(SendCodeApi::class.java)
//                .sendCode(url, mobileNumber)
//            Response.Success(result)
//        } catch (ex: Exception) {
//            Response.Error(message = ex.message)
//        } finally {
//            Response.Loading<SendCodeResponse>(state = false)
//        }
//    }

//    override suspend fun verifyCode(
//        mobileNumber: String,
//        code: String
//    ): Response<VerifyCodeResponse> {
//        return try {
//            Response.Loading<VerifyCodeResponse>(state = true)
//            val baseUrl = preference.baseUrl()
//            val url = if (baseUrl != null) {
//                "$baseUrl/app/vendor/otp/verify"
//            } else {
//                "/app/vendor/otp/verify"
//            }
//            val result = httpClient
//                .create(VerifyCodeApi::class.java)
//                .verify(url, mobileNumber, code)
//            Response.Success(result)
//        } catch (ex: Exception) {
//            Response.Error(message = ex.message)
//        } finally {
//            Response.Loading<VerifyCodeResponse>(state = false)
//        }
//    }

//    override suspend fun verifyCode(
//        mobileNumber: String,
//        code: String
//    ): Response<VerifyCodeResponse> {
//        return try {
//            Response.Loading<VerifyCodeResponse>(state = true)
//            val baseUrl = "https://api.npoint.io"
//            val url = if (baseUrl != null) {
//                "$baseUrl/0dfb3f9001090cec4eee"
//            } else {
//                "/0dfb3f9001090cec4eee"
//            }
//            val result = httpClient
//                .create(VerifyCodeApi::class.java)
//                .verify(url, mobileNumber, code)
//            Response.Success(result)
//        } catch (ex: Exception) {
//            Response.Error(message = ex.message)
//        } finally {
//            Response.Loading<VerifyCodeResponse>(state = false)
//        }
//    }
}