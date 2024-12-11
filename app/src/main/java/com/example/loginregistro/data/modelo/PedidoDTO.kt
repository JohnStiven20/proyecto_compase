package modelo

import java.util.Date

enum class EstadoPedido{PEDIENTE, ENTREGADO, CANCELADO}

data class PedidoDTO (

    val fecha:Date = Date(),
    var precioTotal:Double = 0.0,
    val estado:EstadoPedido = EstadoPedido.ENTREGADO ,
    val listaLineasPedididos:MutableList<LineaPedidoDTO> = mutableListOf()

)