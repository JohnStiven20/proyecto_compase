package modelo

data class PastaDTO(

    var nombre:String,
    var precio:Double,
    var listaIngredienteDTOS:MutableList<IngredienteDTO>
) {

}
