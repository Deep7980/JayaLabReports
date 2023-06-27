package com.jaya.app.labreports.core.domain.repositories

import com.jaya.app.labreports.core.common.enums.QuotationVariance
import com.jaya.app.labreports.core.common.sealed.Response
import com.jaya.app.labreports.core.model.LabQuotationsResponse
import com.jaya.app.labreports.core.model.labReportsDashboardResponse

interface MyQuotesRepository {

//    suspend fun myQuotes(variance: QuotationVariance): Response<LabQuotationsResponse>

    suspend fun myQuotes(): Response<LabQuotationsResponse>

}