package com.example.loginregistro.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginregistro.ui.data.modelo.ProductoDTO
import com.example.loginregistro.ui.data.modelo.Tipo
import modelo.EstadoPedido
import modelo.IngredienteDTO
import modelo.LineaPedidoDTO
import modelo.PedidoDTO
import modelo.Size
import java.util.Date

class HomeViewModel : ViewModel() {

    val productosLiveData = MutableLiveData(generarListaProductos())

    val lineasPedidosLiveData = MutableLiveData(generarListaLineaPedidos())

    val contadorCarritoLiveData: MutableLiveData<Int> = MutableLiveData()

    fun onChangeLineasPedidosLiveDatas(listaLineaPedidos1: List<LineaPedidoDTO>) {
        lineasPedidosLiveData.value = listaLineaPedidos1
    }

    val pedidoLiveData = MutableLiveData(
        PedidoDTO(
            estado = EstadoPedido.PEDIENTE,
            listaLineasPedididos = mutableListOf(),
            precioTotal = 0.0,
            fecha = Date()
        )
    )

    fun onAddCar(id: Int, cantidad: Int, size: Size, productoDTO: ProductoDTO) {
        val pedidoTemporal = PedidoDTO()
        productoDTO.size = size
        val lineaPedidoDTO = LineaPedidoDTO(id, cantidad, productoDTO)
        pedidoTemporal.listaLineasPedididos.add(lineaPedidoDTO)
        val sumaPreciosProductos = pedidoTemporal.listaLineasPedididos
            .map { it.productoDTO.precio}
            .sum()


        pedidoTemporal.listaLineasPedididos.forEach { lineaPedido ->
            println("hola $lineaPedido")
            println("----------------------hola-----------------------------")
        }
        pedidoTemporal.precioTotal = sumaPreciosProductos
        pedidoLiveData.value = pedidoTemporal

    }

    fun onchageConatador(contador1: Int) {
        contadorCarritoLiveData.value = contador1;
    }
}


fun generarListaLineaPedidos(): List<LineaPedidoDTO> {

    return mutableListOf(
        LineaPedidoDTO(
            id = 1,
            cantidad = 50,
            ProductoDTO(
                tipo = Tipo.PIZZA,
                id = 1,
                size = Size.ENANA,
                precio = 5.0,
                nombre = "Pizza",
                listaIngredienteDTO = mutableListOf()
            )
        )
    )
}


fun generarListaProductos(): List<ProductoDTO> {
    // Ingredientes comunes
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

    // Pizzas
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

    // Pastas
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

    // Bebidas
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
