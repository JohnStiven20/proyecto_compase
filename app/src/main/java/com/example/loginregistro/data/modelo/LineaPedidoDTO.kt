package modelo

import com.example.loginregistro.data.modelo.ProductoDTO

data class LineaPedidoDTO(

    val cantidad:Int,
    val productoDTO: ProductoDTO

) {

}