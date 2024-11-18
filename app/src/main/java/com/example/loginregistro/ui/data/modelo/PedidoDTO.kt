package modelo

import java.util.Date

enum class EstadoPedido{PEDIENTE, ENTREGADO, CANCELADO}

data class PedidoDTO (

    val fecha:Date = Date(),
    var precioTotal:Double = 1.5,
    val estado:EstadoPedido = EstadoPedido.PEDIENTE,
    val listaLineasPedididos:MutableList<LineaPedidoDTO> = mutableListOf()

)