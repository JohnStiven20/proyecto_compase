package com.example.loginregistro.data.network

import com.example.loginregistro.data.modelo.LoginDTO
import modelo.ClienteDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ClienteApiService {

    @POST("/clientes/registro")
    suspend fun registrarCliente(@Body cliente: ClienteDTO):  Response<ClienteDTO>
    @POST("/clientes/login")
    suspend fun loginCliente(@Body loginDTO: LoginDTO): Response<ClienteDTO>
    @PUT("/clientes/actualizar")
    suspend fun actualizarCliente(@Body cliente: ClienteDTO): Response<ClienteDTO>
    @GET("/clientes")
    suspend fun getUsers(): Response<List<ClienteDTO>>

}