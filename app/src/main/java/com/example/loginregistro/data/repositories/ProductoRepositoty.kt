package com.example.loginregistro.data.repositories

import com.example.loginregistro.data.modelo.ProductoDTO
import com.example.loginregistro.data.network.ProductoApiService
import okhttp3.Response

class ProductoRepositoty(val apiService: ProductoApiService) {

    suspend fun getProductos(): Result<List<ProductoDTO>> {
        return try {
            val result = apiService.getProductos()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}