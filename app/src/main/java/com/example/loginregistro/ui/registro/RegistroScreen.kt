package com.example.loginregistro.ui.registro

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.loginregistro.R
import com.example.loginregistro.data.componentes.CampoTextoPassword
import com.example.loginregistro.data.componentes.CampoTextoPersonalizado
import com.example.loginregistro.data.componentes.MensajeDeError
import com.example.loginregistro.navigation.Screen
import com.example.loginregistro.ui.errores.ErrorMessage
import modelo.ClienteDTO


@Composable
fun RegistroScreen(registroViewModel: RegistroViewModel, navController: NavController) {

    val clienteDTO: ClienteDTO by registroViewModel.cliente.observeAsState(ClienteDTO())
    val estado: Boolean by registroViewModel.estado.observeAsState(false)
    val errorMessage: ErrorMessage by registroViewModel.errorMessage.observeAsState(ErrorMessage())
    val contexto = LocalContext.current
    val listaCondicionales by registroViewModel.listaCondicionales.observeAsState(
        mutableListOf(false, false, false)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.outline)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.logopizza),
                contentDescription = "Imagen",
                modifier = Modifier
                    .padding(30.dp)
                    .size(width = 300.dp, height = 300.dp)
                    .clip(RoundedCornerShape(percent = 100))
                    .border(3.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(percent = 100))
            )

            CampoTextoPersonalizado(
                nombreCampo = "Nombre",
                text = clienteDTO.nombre,
                Onchage = { valor ->
                    listaCondicionales[0] = valor.isNotBlank() && !valor.matches("^[a-zA-ZÀ-ÿ\\s]+$".toRegex())
                    registroViewModel.onClienteChange(clienteDTO.copy(nombre = valor), listaCondicionales)
                }
            )
            MensajeDeError(mostrar = listaCondicionales[0], mensaje = errorMessage.nombre)

            CampoTextoPersonalizado(
                nombreCampo = "DNI",
                text = clienteDTO.dni,
                Onchage = { valor ->
                    registroViewModel.onClienteChange(clienteDTO.copy(dni = valor), listaCondicionales)
                }
            )

            CampoTextoPassword(
                text = clienteDTO.password,
                Onchage = { valor ->
                    listaCondicionales[1] = valor.length < 4 && valor.isNotBlank()
                    registroViewModel.onClienteChange(clienteDTO.copy(password = valor), listaCondicionales)
                }
            )
            MensajeDeError(mostrar = listaCondicionales[1], mensaje = errorMessage.password)

            CampoTextoPersonalizado(
                nombreCampo = "Telefono",
                text = clienteDTO.telefono,
                Onchage = { valor ->
                    registroViewModel.onClienteChange(clienteDTO.copy(telefono = valor), listaCondicionales)
                }
            )

            CampoTextoPersonalizado(
                nombreCampo = "Email",
                text = clienteDTO.email,
                tipo = KeyboardType.Email,
                Onchage = { valor ->
                    listaCondicionales[2] = valor.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(valor).matches()
                    registroViewModel.onClienteChange(clienteDTO.copy(email = valor), listaCondicionales)
                }
            )
            MensajeDeError(mostrar = listaCondicionales[2], mensaje = errorMessage.email)

            CampoTextoPersonalizado(
                nombreCampo = "Dirección",
                text = clienteDTO.direccion,
                Onchage = { valor ->
                    registroViewModel.onClienteChange(clienteDTO.copy(direccion = valor), listaCondicionales)
                }
            )

            TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Text("¡Accede con tu cuenta!")
            }

            Button(
                onClick = {
                    registroViewModel.onRegistrarClick(onProgress = { progress ->
                        if (progress) {
                          navController.navigate(Screen.Home.route)
                        } else {
                            Toast.makeText(contexto, "El email ya existe", Toast.LENGTH_SHORT).show()
                        }
                    })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .size(51.dp),
                enabled = estado
            ) {
                Text("Registrar")
            }
        }
    }
}



