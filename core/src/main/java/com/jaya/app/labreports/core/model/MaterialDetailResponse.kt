package com.jaya.app.labreports.core.model

import com.jaya.app.labreports.core.domain.entities.Materials

data class MaterialDetailResponse(
    val status:Boolean,
    val message: String,
    val materials: Materials
)