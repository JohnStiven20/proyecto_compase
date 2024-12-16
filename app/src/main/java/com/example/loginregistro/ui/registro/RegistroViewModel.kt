package com.example.loginregistro.ui.registro


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginregistro.data.repositories.ClienteRepository
import com.example.loginregistro.ui.errores.ErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClienteDTO
import retrofit2.*


class RegistroViewModel( var clienteRepository: ClienteRepository) : ViewModel() {


    val cliente = MutableLiveData(
        ClienteDTO(
            nombre = "",
            dni = "",
            email = "",
            telefono = "",
            password = "",
            direccion = ""
        )
    )


    val errorMessage =
        MutableLiveData(
            ErrorMessage(
                nombre = "El numero no puede contener numeros",
                email = "El formato incorrecto",
                password = "La contraseña tiene que tener 4 caracteres"
            )
        )

    val estado = MutableLiveData(false)
    val listaCondicionales = MutableLiveData(mutableListOf(false, false, false))


    fun onClienteChange(clienteDTO: ClienteDTO, listaCondicioanes: MutableList<Boolean>): Unit {

        clienteDTO.let { valor ->
            listaCondicioanes.let { valor2 ->

                if (valor.nombre.isNotBlank()
                    && valor.email.isNotBlank()
                    && valor.password.isNotBlank()
                    && valor.direccion.isNotBlank()
                    && valor.telefono.isNotBlank()
                    && valor.dni.isNotBlank() &&
                    !valor2[0] && !valor2[1] && !valor2[2]
                ) {
                    estado.value = true;
                } else {
                    estado.value = false
                }
            }
        }

        cliente.value = clienteDTO;
        listaCondicionales.value = listaCondicioanes

    }

    fun registrarCliente(cliente: ClienteDTO, onSuccess: () -> Unit, onError: (String) -> Unit) {

        viewModelScope.launch(Dispatchers.IO)

        {
            try {
                val response = clienteRepository.registrarCliente(cliente)

                if (response.isSuccess) {
                    onSuccess() // Llamamos a la función de éxito (puede actualizar la UI, etc.)
                } else {
                    onError("Error: ${response.getOrThrow()}") // Pasamos el mensaje de error
                }
            } catch (e: Exception) {
                onError("Exception: ${e.message}")
            }
        }
    }



    fun onRegistrarClick() {
        val clienteActual = cliente.value

        if (clienteActual != null) {
            viewModelScope.launch {

                val result = clienteRepository.registrarCliente(clienteActual)
                withContext(Dispatchers.Main) {

                    when (result.isSuccess) {
                        true -> {
                            cliente.value = result.getOrThrow()
                        }
                        false -> {
                            Log.d("REGISTRO", "Error:$result")
                        }
                    }
                }
            }
        }
    }





    fun onRegistrarClick(cliente: ClienteDTO) {
        Log.d("Registro", "Objeto Registro: $cliente")
    }
}