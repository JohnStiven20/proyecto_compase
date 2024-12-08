package com.example.loginregistro.ui.registro


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginregistro.ui.data.modelo.LoginDTO
import com.example.loginregistro.ui.errores.ErrorMessage
import modelo.ClienteDTO

class RegistroViewModel : ViewModel() {

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


    fun OnClienteChange(clienteDTO: ClienteDTO,listaCondicioanes:MutableList<Boolean> ) :Unit{

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
                    } else{
                        estado.value =  false
                    }
            }

        }

        cliente.value = clienteDTO;
        listaCondicionales.value = listaCondicioanes

    }

    fun OnRegistrarClick(cliente:ClienteDTO) {
        Log.d("Registro", "Objeto Registro: $cliente")
    }
}