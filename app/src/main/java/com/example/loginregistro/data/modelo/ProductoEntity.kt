package com.example.loginregistro.data.modelo

import modelo.IngredienteDTO
import modelo.Size

data class ProductoEntity(
    val id:Int = 0,
    var nombre:String = "",
    var precio:Double  = 0.0,
    var size: String = "",
    var ingredientes: List<IngredienteDTO> = mutableListOf(),
    var tipo: String = ""
) {


}