package modelo

import com.example.loginregistro.ui.data.modelo.ProductoDTO

data class LineaPedidoDTO(

    val cantidad:Int,
    val productoDTO: ProductoDTO

) {

}