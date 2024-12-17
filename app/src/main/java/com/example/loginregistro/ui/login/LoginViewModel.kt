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
    val existoso = MutableLiveData(false)

    fun registrarCliente(cliente: ClienteDTO, onSuccess: () -> Unit, onError: (String) -> Unit) {

        viewModelScope.launch(Dispatchers.IO)

        {
            try {
                val response = clienteRepository.registrarCliente(cliente)

                if (response.isSuccess) {
                    onSuccess()
                } else {
                    onError("Error: ${response.getOrThrow()}") // Pasamos el mensaje de error
                }
            } catch (e: Exception) {
                onError("Exception: ${e.message}")
            }
        }
    }

    fun onLoginDTOChange(login: LoginDTO) {

        if (login.password.isNotBlank() && login.email.isNotBlank()) {
            estado.value = true
        } else {
            estado.value = false
        }

        this.login.value = login

    }

    fun onLoginClick(onSuccess: (Boolean) -> Unit) {
        val loginDTO = login.value
        existoso.value = true
        if (loginDTO != null) {
            viewModelScope.launch {
                val result = clienteRepository.loginCliente(loginDTO)
                withContext(Dispatchers.Main) {

                    when (result.isSuccess) {
                        true -> {
                            cliente.value = result.getOrThrow()
                            Log.d("Login Malo", "VIA españa Error:$cliente")
                            onSuccess(true)
                            existoso.value = false
                        }
                        false -> {
                            onSuccess(false)
                            existoso.value = false
                            Log.d("Login Malo", "VIA FRANCIA Error:$result")
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