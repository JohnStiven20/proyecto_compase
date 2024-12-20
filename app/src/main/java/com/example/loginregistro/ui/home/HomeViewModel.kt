package com.example.loginregistro.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginregistro.data.modelo.ProductoDTO
import com.example.loginregistro.data.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.EstadoPedido
import modelo.LineaPedidoDTO
import modelo.PedidoDTO
import java.util.Date

class HomeViewModel(private val productoRepository: ProductRepository) : ViewModel() {



    val productosLiveData = MutableLiveData(listOf(ProductoDTO()))
    val contadorCarritoLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val pedidoLiveData: MutableLiveData<PedidoDTO?> = MutableLiveData<PedidoDTO?>(null)
    val loading = MutableLiveData(false);

    init {
        getProductos()
    }


    fun onAddCar(cantidad: Int, size: String, productoDTO: ProductoDTO) {

        var pedidoTemporal: PedidoDTO? = pedidoLiveData.value
        val productoTemporal = ProductoDTO()

        if (pedidoTemporal == null) {

            pedidoTemporal = PedidoDTO(fecha = Date(), estado = EstadoPedido.PEDIENTE)

            productoTemporal.copy(
                precio = productoDTO.precio,
                nombre = productoDTO.nombre,
                tipo = productoDTO.tipo,
                ingredientes = productoDTO.ingredientes,
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
                ingredientes = productoDTO.ingredientes,
                size = size
            )

            pedidoTemporal.listaLineasPedididos.add(LineaPedidoDTO(cantidad, productoTemporal))
            pedidoTemporal.precioTotal = pedidoTemporal.listaLineasPedididos.sumOf {it.productoDTO.precio }

            contadorCarritoLiveData.value = pedidoTemporal.listaLineasPedididos.sumOf { it.cantidad }
            pedidoLiveData.value = pedidoTemporal
        }

        Log.d("Pedido" , "${pedidoLiveData.value}")

    }

    private fun getProductos() {
        viewModelScope.launch {
            loading.value = true
            val result = withContext(Dispatchers.IO) {
                productoRepository.getProductos()
            }
            loading.value = false

            result.onSuccess { lista ->
                productosLiveData.value = lista
                Log.d("getProductos", "Productos cargados correctamente")
            }.onFailure { error ->
                Log.e("getProductos", "Error al cargar productos: $error")
            }
        }
    }
}
