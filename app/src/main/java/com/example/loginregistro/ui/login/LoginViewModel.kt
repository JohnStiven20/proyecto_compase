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


class LoginViewModel(private val clienteRepository: ClienteRepository) : ViewModel() {

    val loginLiveData = MutableLiveData(LoginDTO())
    val estadoLiveData = MutableLiveData(false)
    val loadingLiveData = MutableLiveData(false)


    fun onLoginDTOChange(login: LoginDTO) {
        estadoLiveData.value = login.password.isNotBlank() && login.email.isNotBlank()
        this.loginLiveData.value = login
    }

    fun onLoginClick(onSuccess: (Boolean) -> Unit) {
        val loginDTO = loginLiveData.value
        loadingLiveData.value = true
        if (loginDTO != null) {
            viewModelScope.launch {
                val result = clienteRepository.loginCliente(loginDTO)

                withContext(Dispatchers.Main) {

                    when (result.isSuccess) {
                        true -> {
                            loadingLiveData.value = false
                            onSuccess(true)
                        }
                        false -> {
                            loadingLiveData.value = false
                            onSuccess(false)
                        }
                    }
                }
            }
        }
    }
}