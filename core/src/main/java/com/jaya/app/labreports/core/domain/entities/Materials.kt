package com.jaya.app.labreports.core.domain.entities

data class Materials(
    val id:String,
    val name: String,
    val Quantity:String,
    val isPresent:Boolean,
    val receivedOn: String,
    val materialList : List<String>,
    val value : List<Float>,
//    val values : List<String>
)

//object getMaterialList{
//
//    fun materialList():MutableList<Materials>{
//        val list = mutableListOf<Materials>()
//        list.add(Materials(listOf("Moisture","Total Ash","Acid Insoluable Ash","Gluten","Alcholoic Acidity","Granularity","Uric Acid"),
//            listOf("% by mass, Max","(on dry basis) % by mass, Max","(on dry basis) % by mass, Max","(on dry basis) % by mass, Min","(as H2SO4)in 90% alcholoic","","mg/100gm max") ,
//            ))
//        return list
//    }
//}


