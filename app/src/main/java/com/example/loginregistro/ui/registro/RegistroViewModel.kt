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

    fun registrarCliente(
        clienteActual: ClienteDTO,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = clienteRepository.tareaRegistrarCliente(clienteActual)
                withContext(Dispatchers.IO) {
                    result.fold(
                        onSuccess = {
                            cliente.value = it
                            onSuccess()
                        },
                        onFailure = { error ->
                            onError(error.message ?: "Unknown error")
                        }
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Default) {
                    onError("Exception: ${e.message}")
                }
            }
        }
    }



    fun onRegistrarClick(cliente: ClienteDTO) {
        Log.d("Registro", "Objeto Registro: $cliente")
    }
}