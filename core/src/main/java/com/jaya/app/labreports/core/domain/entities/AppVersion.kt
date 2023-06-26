package com.jaya.app.labreports.core.domain.entities

data class AppVersion(

    val name: String,
    val isUpdateAvailable: Boolean,
    val description: String,
    val link: String? = null,
    val isOptional: Boolean

)
