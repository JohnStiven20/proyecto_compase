package com.example.loginregistro.ui.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.loginregistro.R
import com.example.loginregistro.data.componentes.CampoTextoPassword
import com.example.loginregistro.data.componentes.CampoTextoPersonalizado
import com.example.loginregistro.data.componentes.MensajeDeError
import com.example.loginregistro.data.modelo.LoginDTO
import com.example.loginregistro.navigation.Screen
import modelo.ClienteDTO


@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {


    val loginDTO: LoginDTO by loginViewModel.login.observeAsState(
        LoginDTO(
            email = "",
            password = ""
        )
    )
    val estado: Boolean by loginViewModel.estado.observeAsState(false)
    val clienteDTO by loginViewModel.cliente.observeAsState(ClienteDTO(email = "algo"))
    var existo by rememberSaveable { mutableStateOf(false) }
    val existoso by loginViewModel.existoso.observeAsState(false)
    var variableLocal by rememberSaveable { mutableStateOf(false)}

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
                contentDescription = "dwd",
                modifier = Modifier
                    .padding(30.dp)
                    .size(width = 300.dp, height = 300.dp)
                    .clip(shape = RoundedCornerShape(percent = 100))
                    .border(
                        3.dp,
                        MaterialTheme.colorScheme.onBackground,
                        RoundedCornerShape(percent = 100)
                    ),
            )

            Spacer(modifier = Modifier.height(100.dp))

            CampoTextoPersonalizado(
                nombreCampo = "gmail",
                text = loginDTO.email,
                tipo = KeyboardType.Email,
                Onchage = { loginViewModel.onLoginDTOChange(loginDTO.copy(email = it)) }
            )

            MensajeDeError(mostrar = existo, mensaje = "el correo esta mal")

            CampoTextoPassword(
                text = loginDTO.password,
                Onchage = { loginViewModel.onLoginDTOChange(loginDTO.copy(password = it)) })

            MensajeDeError(mostrar = existo, mensaje = "La contraseña esta mal")

            TextButton(onClick = { navController.navigate(Screen.Registro.route) }) {
                Text(text = "Iniciar sesion")
            }

            Button(
                onClick = {
                    loginViewModel.onLoginClick(onSuccess = { progress ->

                        if (progress) {
                            Log.d("Login Malo", "Error:$clienteDTO")
                            if (loginDTO.email == clienteDTO.email && loginDTO.password == clienteDTO.password) {
                                Log.d("Login Malo", "Error:$clienteDTO")
                                navController.navigate(Screen.Home.route)
                            } else {
                                Log.d("Login Malo", "Error:$clienteDTO")
                            }
                        } else {
                            existo = true
                        }
                    })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .size(51.dp),
                enabled = estado

            ) {
                Text("Login")
            }

             if (existoso) {
                CircularProgressIndicator()
             }


        }
    }
}

