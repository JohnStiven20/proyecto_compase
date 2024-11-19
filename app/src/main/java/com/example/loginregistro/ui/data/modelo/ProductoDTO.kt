package com.example.loginregistro.ui.data.modelo

import modelo.IngredienteDTO
import modelo.Size

data class ProductoDTO(
    val id:Int = 0,
    var nombre:String = "",
    var precio:Double  = 0.0,
    var size: Size = Size.GRANDE,
    var listaIngredienteDTO: MutableList<IngredienteDTO> = mutableListOf(),
    var tipo: Tipo = Tipo.PIZZA
) {


}