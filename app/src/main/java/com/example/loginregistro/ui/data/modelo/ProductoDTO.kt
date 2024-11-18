package com.example.loginregistro.ui.data.modelo

import modelo.IngredienteDTO
import modelo.Size

data class ProductoDTO(
    val id:Int = 0,
    val nombre:String = "",
    val precio:Double  = 0.0,
    var size: Size = Size.GRANDE,
    val listaIngredienteDTO: MutableList<IngredienteDTO> = mutableListOf(),
    var tipo: Tipo = Tipo.PIZZA
) {


}