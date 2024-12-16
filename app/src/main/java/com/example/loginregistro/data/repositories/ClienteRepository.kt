package com.example.loginregistro.data.repositories

import com.example.loginregistro.data.modelo.LoginDTO
import com.example.loginregistro.data.network.ClienteApiService
import modelo.ClienteDTO


class ClienteRepository(private val apiService: ClienteApiService) {

    suspend fun registrarCliente(cliente: ClienteDTO): Result<ClienteDTO> {
        return try {
            val response = apiService.registrarCliente(cliente)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginCliente(loginDTO: LoginDTO): Result<ClienteDTO> {
        return try {
            val response = apiService.loginCliente(loginDTO)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

