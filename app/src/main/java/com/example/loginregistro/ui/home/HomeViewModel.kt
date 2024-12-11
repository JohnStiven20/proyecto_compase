package com.example.loginregistro.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginregistro.data.modelo.ProductoDTO
import com.example.loginregistro.data.modelo.Tipo
import com.example.loginregistro.data.repositories.ProductoRepositoty
import modelo.EstadoPedido
import modelo.IngredienteDTO
import modelo.LineaPedidoDTO
import modelo.PedidoDTO
import modelo.Size
import java.util.Date

class HomeViewModel(productoRepositoty: ProductoRepositoty) : ViewModel() {

    val productosLiveData = MutableLiveData(generarListaProductos())

    val contadorCarritoLiveData: MutableLiveData<Int> = MutableLiveData(0)

    val pedidoLiveData: MutableLiveData<PedidoDTO?> = MutableLiveData<PedidoDTO?>(null)

    fun onAddCar(cantidad: Int, size: Size, productoDTO: ProductoDTO) {

        var pedidoTemporal: PedidoDTO? = pedidoLiveData.value
        val productoTemporal = ProductoDTO()

        if (pedidoTemporal == null) {

            pedidoTemporal = PedidoDTO(fecha = Date(), estado = EstadoPedido.PEDIENTE)

            productoTemporal.copy(
                precio = productoDTO.precio,
                nombre = productoDTO.nombre,
                tipo = productoDTO.tipo,
                listaIngredienteDTO = productoDTO.listaIngredienteDTO,
                size = size
            )

            pedidoTemporal.listaLineasPedididos.add(LineaPedidoDTO(cantidad, productoTemporal))
            pedidoTemporal.precioTotal = pedidoTemporal.listaLineasPedididos.sumOf { it.productoDTO.precio * it.cantidad }
            contadorCarritoLiveData.value = pedidoTemporal.listaLineasPedididos.sumOf { it.cantidad }
            pedidoLiveData.value = pedidoTemporal

        } else {

            productoTemporal.copy(
                precio = productoDTO.precio,
                nombre = productoDTO.nombre,
                tipo = productoDTO.tipo,
                listaIngredienteDTO = productoDTO.listaIngredienteDTO,
                size = size
            )

            pedidoTemporal.listaLineasPedididos.add(LineaPedidoDTO(cantidad, productoTemporal))
            pedidoTemporal.precioTotal = pedidoTemporal.listaLineasPedididos.sumOf {it.productoDTO.precio }

            contadorCarritoLiveData.value = pedidoTemporal.listaLineasPedididos.sumOf { it.cantidad }
            pedidoLiveData.value = pedidoTemporal
        }

        Log.d("Pedido" , "${pedidoLiveData.value}")

    }



}

fun generarListaProductos(): List<ProductoDTO> {

    val ingredienteQueso = IngredienteDTO(nombre = "Queso")
    val ingredienteTomate = IngredienteDTO(nombre = "Tomate")
    val ingredienteJamon = IngredienteDTO(nombre = "Jamón")
    val ingredientePepperoni = IngredienteDTO(nombre = "Pepperoni")
    val ingredienteChampinones = IngredienteDTO(nombre = "Champiñones")
    val ingredientePollo = IngredienteDTO(nombre = "Pollo")
    val ingredienteCebolla = IngredienteDTO(nombre = "Cebolla")
    val ingredientePimiento = IngredienteDTO(nombre = "Pimiento")
    val ingredienteAceitunas = IngredienteDTO(nombre = "Aceitunas")
    val ingredienteBacon = IngredienteDTO(nombre = "Bacon")

    val pizzas = listOf(
        ProductoDTO(
            id = 1,
            nombre = "Pizza Margarita",
            precio = 8.99,
            size = Size.GRANDE,
            listaIngredienteDTO = mutableListOf(ingredienteQueso, ingredienteTomate),
            tipo = Tipo.PIZZA
        ),
        ProductoDTO(
            id = 2,
            nombre = "Pizza Pepperoni",
            precio = 10.99,
            size = Size.GRANDE,
            listaIngredienteDTO = mutableListOf(
                ingredienteQueso,
                ingredienteTomate,
                ingredientePepperoni
            ),
            tipo = Tipo.PIZZA
        ),
        ProductoDTO(
            id = 3,
            nombre = "Pizza Vegetariana",
            precio = 9.99,
            size = Size.GRANDE,
            listaIngredienteDTO = mutableListOf(
                ingredienteQueso,
                ingredienteTomate,
                ingredientePimiento,
                ingredienteCebolla,
                ingredienteAceitunas,
                ingredienteChampinones
            ),
            tipo = Tipo.PIZZA
        ),
        ProductoDTO(
            id = 4,
            nombre = "Pizza Hawaiana",
            precio = 10.49,
            size = Size.GRANDE,
            listaIngredienteDTO = mutableListOf(
                ingredienteQueso,
                ingredienteJamon,
                IngredienteDTO(nombre = "Piña")
            ),
            tipo = Tipo.PIZZA
        ),
        ProductoDTO(
            id = 5,
            nombre = "Pizza BBQ Chicken",
            precio = 13.99,
            size = Size.GRANDE,
            listaIngredienteDTO = mutableListOf(
                ingredienteQueso,
                ingredientePollo,
                ingredienteCebolla,
                ingredienteBacon,
                IngredienteDTO(nombre = "Salsa BBQ")
            ),
            tipo = Tipo.PIZZA
        )
    )

    val pastas = listOf(
        ProductoDTO(
            id = 6,
            nombre = "Lasaña de Carne",
            precio = 12.99,
            size = Size.GRANDE,
            listaIngredienteDTO = mutableListOf(
                ingredienteQueso,
                ingredienteTomate,
            ),
            tipo = Tipo.PASTA
        ),
        ProductoDTO(
            id = 7,
            nombre = "Espagueti Carbonara",
            precio = 11.49,
            size = Size.MEDIANO,
            listaIngredienteDTO = mutableListOf(
                ingredienteQueso,
                ingredienteBacon,
                IngredienteDTO(nombre = "Crema de Leche")
            ),
            tipo = Tipo.PASTA
        ),
        ProductoDTO(
            id = 8,
            nombre = "Fetuccini Alfredo",
            precio = 13.49,
            size = Size.GRANDE,
            listaIngredienteDTO = mutableListOf(
                ingredienteQueso,
                IngredienteDTO(nombre = "Salsa Alfredo"),
                ingredientePollo
            ),
            tipo = Tipo.PASTA
        )
    )

    val bebidas = listOf(
        ProductoDTO(
            id = 9,
            nombre = "Coca-Cola",
            precio = 1.99,
            size = Size.ENANA,
            listaIngredienteDTO = mutableListOf(),
            tipo = Tipo.BEBIDA
        ),
        ProductoDTO(
            id = 10,
            nombre = "Agua Mineral",
            precio = 0.99,
            size = Size.ENANA,
            listaIngredienteDTO = mutableListOf(),
            tipo = Tipo.BEBIDA
        ),
        ProductoDTO(
            id = 11,
            nombre = "Cerveza Artesanal",
            precio = 3.99,
            size = Size.ENANA,
            listaIngredienteDTO = mutableListOf(),
            tipo = Tipo.BEBIDA
        )
    )

    return pizzas + pastas + bebidas
}
