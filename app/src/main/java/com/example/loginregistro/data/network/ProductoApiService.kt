package com.example.loginregistro.data.network

import com.example.loginregistro.data.modelo.ProductoDTO
import modelo.ClienteDTO
import retrofit2.Response
import retrofit2.http.GET

interface ProductoApiService {

    @GET("/productos")
    suspend fun getProductos(): List<ProductoDTO>
}