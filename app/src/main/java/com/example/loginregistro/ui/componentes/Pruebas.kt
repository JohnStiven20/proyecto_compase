package com.example.loginregistro.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import modelo.Size

@Composable
fun MenuPizzeria(): Size {

    var expanded by rememberSaveable{ mutableStateOf(false) }
    var texto by rememberSaveable{ mutableStateOf("selecciona tamaño") }
    var tipo by rememberSaveable {mutableStateOf(Size.NADA)}

    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {



        TextButton(onClick = { expanded = true }) {
            Text(texto)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Grande") },
                onClick = {
                    expanded = false
                    texto = "Grande"
                    tipo = Size.GRANDE
                },
            )

            DropdownMenuItem(
                text = { Text("Mediano") },
                onClick = {
                    expanded = false
                    texto = "Mediano"
                    tipo = Size.MEDIANO

                },
            )

            DropdownMenuItem(
                text = { Text("Enano") },
                onClick = {
                    expanded = false
                    texto = "Enano"
                    tipo = Size.ENANA
                },
            )

            DropdownMenuItem(
                text = { Text("Nada") },
                onClick = {
                    expanded = false
                    texto = "selecciona tamaño"
                    tipo = Size.NADA
                },
            )
        }
    }

    return tipo
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MaterialTheme {
        AppTheme {

            //HomeScreen(HomeViewModel())

            //AppNavigation()
            /**
             *  RegistroScreen(RegistroViewModel(), rememberNavController())
             *  LoginScreen(LoginViewModel(),rememberNavController() )
             */
            /**
             *  RegistroScreen(RegistroViewModel(), rememberNavController())
             *  LoginScreen(LoginViewModel(),rememberNavController() )
             */
            /**
             *  RegistroScreen(RegistroViewModel(), rememberNavController())
             *  LoginScreen(LoginViewModel(),rememberNavController() )
             */
            /**
             *  RegistroScreen(RegistroViewModel(), rememberNavController())
             *  LoginScreen(LoginViewModel(),rememberNavController() )
             */

        }

    }
}