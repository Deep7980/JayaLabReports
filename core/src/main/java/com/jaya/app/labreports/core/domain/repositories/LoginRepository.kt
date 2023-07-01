package com.jaya.app.labreports.core.domain.repositories

import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.model.SendCodeResponse
import com.jaya.app.labreports.core.model.VerifyCodeResponse

interface LoginRepository {

    suspend fun sendCode(mobileNumber: String): Response<SendCodeResponse>
//
//    suspend fun verifyCode(mobileNumber: String, code: String): Response<VerifyCodeResponse>
}