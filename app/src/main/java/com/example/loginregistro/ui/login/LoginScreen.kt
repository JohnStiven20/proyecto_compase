package com.example.loginregistro.ui.login

import android.util.Patterns
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.loginregistro.R
import com.example.loginregistro.data.componentes.CampoTextoPassword
import com.example.loginregistro.data.componentes.CampoTextoPersonalizado
import com.example.loginregistro.data.modelo.LoginDTO
import com.example.loginregistro.navigation.Screen


@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {


    val loginDTO: LoginDTO by loginViewModel.login.observeAsState(LoginDTO(email = "", password = ""))
    val estado: Boolean by loginViewModel.estado.observeAsState(false)
    //val errorMessage: ErrorMessage by loginViewModel.errorMessage.observeAsState(ErrorMessage())

    val listaCondicionales by loginViewModel.listaCondicionales.observeAsState(
        mutableListOf(
            false,
            false
        )
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
                Onchage = { valor ->
                    listaCondicionales[0] = !Patterns.EMAIL_ADDRESS.matcher(valor).matches() && valor.isNotBlank()
                    loginViewModel.OnClienteChange(loginDTO.copy(email = valor))
                    //loginViewModel.OnChagelistaCondicionales(listaCondicionales)
                }
            )

            //MensajeDeError(mostrar = listaCondicionales[0], mensaje = errorMessage.email)

            CampoTextoPassword(
                text = loginDTO.password,
                Onchage = { valor ->
                    listaCondicionales[1] = valor.length < 4 && valor.isNotBlank()
                    loginViewModel.OnClienteChange(loginDTO.copy(password = valor))
                    //loginViewModel.OnChagelistaCondicionales(listaCondicionales)
                })

           // MensajeDeError(mostrar = listaCondicionales[1], mensaje = errorMessage.password)

            TextButton(onClick = { navController.navigate(Screen.Registro.route)}) {
                Text(text = "Iniciar sesion")
            }


            Button(
                onClick = {
                    loginViewModel.OnRegistrarClick(loginDTO)
                    navController.navigate(Screen.Home.route)
                    loginViewModel.onLoginClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .size(51.dp),
                enabled = estado

            ) {
                Text("Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        AppTheme {

            //HomeScreen(HomeViewModel())

            val navController = rememberNavController()
            //LoginScreen(loginViewModel = LoginViewModel(), navController = navController)
            //AppNavigation(navController = navController)


        }
    }
}