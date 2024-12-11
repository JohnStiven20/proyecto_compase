package com.example.loginregistro.data.repositories

import android.util.Log
import com.example.loginregistro.data.network.ClienteApiService
import modelo.ClienteDTO
import okhttp3.ResponseBody
import retrofit2.Response


class ClienteRepository(private val apiService: ClienteApiService) {

    suspend fun tareaRegistrarCliente(cliente: ClienteDTO): Result<ClienteDTO> {
        return try {
            val response = apiService.registrarCliente(cliente)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("HTTP error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
