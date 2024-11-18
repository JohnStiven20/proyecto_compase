package modelo

data class IngredienteDTO(

    var nombre:String,
    var listaAlergenos:MutableList<String> = mutableListOf()

) {


}