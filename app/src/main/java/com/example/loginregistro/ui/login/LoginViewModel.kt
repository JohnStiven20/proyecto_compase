package com.example.loginregistro.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginregistro.data.modelo.LoginDTO
import com.example.loginregistro.data.repositories.ClienteRepository
import com.example.loginregistro.ui.errores.ErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClienteDTO


class LoginViewModel(val clienteRepository: ClienteRepository) : ViewModel() {

    val login = MutableLiveData(
        LoginDTO(
            email = "",
            password = ""
        )
    )

    val cliente = MutableLiveData(ClienteDTO())

    val estado = MutableLiveData(false)

    val listaCondicionales = MutableLiveData(mutableListOf(false, false))

    val errorMessage =
        MutableLiveData(
            ErrorMessage(
                nombre = "El numero no puede contener numeros",
                email = "El formato incorrecto"
            )
        )

    fun onLoginDTOChange(login: LoginDTO) {

        if (login.password.isNotBlank() && login.email.isNotBlank()) {
            estado.value = true
        } else {
            estado.value = false
        }

        this.login.value = login

    }

    fun onLoginClick() {
        val loginDTO = login.value
        if (loginDTO != null) {

            viewModelScope.launch {
                val result = clienteRepository.loginCliente(loginDTO)

                withContext(Dispatchers.Main) {

                    when (result.isSuccess) {
                        true -> {
                            cliente.value = result.getOrNull()
                            Log.d("Login exitoso:", "${cliente.value}")

                        }
                        false -> {
                            Log.d("REGISTRO", "Error:$result")
                        }
                    }
                }
            }
        }
    }




    fun onRegistrarClick(login: LoginDTO) {
        Log.d("Logear", "Objeto Login: $login")
    }

}