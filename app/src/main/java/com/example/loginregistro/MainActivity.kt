package com.example.loginregistro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.loginregistro.ui.componentes.AppNavigation
import com.example.loginregistro.ui.home.HomeScreen
import com.example.loginregistro.ui.home.HomeViewModel
import com.example.loginregistro.ui.login.LoginScreen
import com.example.loginregistro.ui.login.LoginViewModel
import com.example.loginregistro.ui.registro.RegistroScreen
import com.example.loginregistro.ui.registro.RegistroViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingPreview()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        AppTheme {

            HomeScreen(HomeViewModel())

            //AppNavigation()
            /**
             *  RegistroScreen(RegistroViewModel(), rememberNavController())
             *  LoginScreen(LoginViewModel(),rememberNavController() )
             */

        }

    }
}



