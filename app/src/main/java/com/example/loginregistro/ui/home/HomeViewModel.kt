package com.example.loginregistro.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginregistro.data.Mapper
import com.example.loginregistro.data.modelo.ProductoDTO
import com.example.loginregistro.data.modelo.ProductoEntity
import com.example.loginregistro.data.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.EstadoPedido
import modelo.LineaPedidoDTO
import modelo.PedidoDTO
import modelo.Size
import java.util.Date

class HomeViewModel(private val productoRepository: ProductRepository) : ViewModel() {

    val productosLiveData: MutableLiveData<MutableList<ProductoDTO>> = MutableLiveData(mutableStateListOf())
    var listaProductos:List<ProductoEntity> = mutableStateListOf()
    val contadorCarritoLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val pedidoLiveData: MutableLiveData<PedidoDTO?> = MutableLiveData<PedidoDTO?>(null)
    val loading = MutableLiveData(false);


    init {
        getProductos()
    }


    fun onAddCar(cantidad: Int, size: Size, productoDTO: ProductoDTO) {

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


            withContext(Dispatchers.Main) {
               val result = productoRepository.getProductos()

                when(result.isSuccess) {
                    true -> {
                        listaProductos =   result.getOrThrow()
                        productosLiveData.value =  Mapper.jsonToProducto(listaProductos)
                    }
                    false -> {
                        Log.d("HOME", "Error al cargar productos: ${result.exceptionOrNull()}")
                    }
                }
            }

            loading.value = false

        }
    }

}
