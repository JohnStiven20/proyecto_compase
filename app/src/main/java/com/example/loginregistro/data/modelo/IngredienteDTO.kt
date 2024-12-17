package modelo

data class IngredienteDTO(

    var nombre:String,
    var alergenos:List<String> = mutableListOf()

) {


}