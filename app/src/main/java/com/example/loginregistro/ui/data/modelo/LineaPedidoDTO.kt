package modelo

import com.example.loginregistro.ui.data.modelo.ProductoDTO

data class LineaPedidoDTO(

    val id:Int,
    val cantidad:Int,
    val productoDTO: ProductoDTO

) {

}