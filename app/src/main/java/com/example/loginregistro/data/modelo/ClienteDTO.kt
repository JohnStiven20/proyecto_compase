package modelo

data class ClienteDTO(

    var dni:String = "",
    var nombre:String = "",
    var direccion:String = "",
    var telefono:String = "",
    var email:String = "",
    var password:String = ""

) {

    override fun toString(): String {
        return "ClienteDTO(dni='$dni', nombre='$nombre', direccion='$direccion', telefono='$telefono', email='$email', password='$password')"
    }
}