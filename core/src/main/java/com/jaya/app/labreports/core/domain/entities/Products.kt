package com.jaya.app.labreports.core.domain.entities

import com.jaya.app.labreports.core.common.enums.QuotationType

data class Products(
    val id:String,
    val name: String,
    val Quantity:String,
    val receivedOn: String,
    val image: String
)
//data class MaterialDetails(
//
//)
object ItemIdToUpdate{
    var IdToUpdate:String=""
}
