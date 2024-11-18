package modelo

data class PizzaDTO (

    val nombre:String,
    val precio:Double,
    val size: Size,
    val listaIngredienteDTO: MutableList<IngredienteDTO> = mutableListOf()

) {


}
