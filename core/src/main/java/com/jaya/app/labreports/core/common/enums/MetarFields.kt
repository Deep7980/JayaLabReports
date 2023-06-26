package com.jaya.app.labreports.core.common.enums

enum class MetarFields(val value: String) {

    BaseUrl("static_base_url");

    operator fun invoke() = value
}