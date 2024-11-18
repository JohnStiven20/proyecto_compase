package com.example.loginregistro.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginregistro.ui.data.modelo.LoginDTO
import com.example.loginregistro.ui.errores.ErrorMessage

class LoginViewModel : ViewModel() {

    val login = MutableLiveData(
        LoginDTO(
            email = "",
            password = ""
        )
    )

    val estado = MutableLiveData(false)

    val listaCondicionales = MutableLiveData(mutableListOf(false, false))

    val errorMessage =
        MutableLiveData(
            ErrorMessage(
                nombre = "El numero no puede contener numeros",
                email = "El formato incorrecto"
            )
        )

    fun OnClienteChange(loginDTO: LoginDTO): Unit {

        login.value = loginDTO;

        login.value?.let { valor ->
            listaCondicionales.value?.let { valor2 ->

                if (valor.email.isNotBlank()
                    && valor.password.isNotBlank()
                    && !valor2[0] && !valor2[1]
                ) {
                    estado.value = true;
                } else {
                    estado.value = false
                }
            }
        }
    }


    fun OnRegistrarClick(login: LoginDTO) {
        Log.d("Logear", "Objeto Login: $login")
    }

    fun OnChagelistaCondicionales(listConditional: MutableList<Boolean>) {
        this.listaCondicionales.value = listConditional
    }

}