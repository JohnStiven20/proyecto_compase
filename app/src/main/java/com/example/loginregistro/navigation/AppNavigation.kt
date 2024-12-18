package com.example.loginregistro.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.loginregistro.data.network.Retrofitinstance
import com.example.loginregistro.data.repositories.ClienteRepository
import com.example.loginregistro.data.repositories.ProductRepository
import com.example.loginregistro.ui.home.HomeScreen
import com.example.loginregistro.ui.home.HomeViewModel
import com.example.loginregistro.ui.login.LoginScreen
import com.example.loginregistro.ui.login.LoginViewModel
import com.example.loginregistro.ui.registro.RegistroScreen
import com.example.loginregistro.ui.registro.RegistroViewModel


@Composable
fun AppNavigation(navController:  NavHostController) {


    val clienteApiService = Retrofitinstance.clienteApi
    val productoApiService = Retrofitinstance.productoApi
    val clienteRepository = ClienteRepository(apiService = clienteApiService)
    val productoRepository = ProductRepository(apiService = productoApiService)

    NavHost(navController = navController, startDestination = Screen.Registro.route) {

        composable (
            route = Screen.Registro.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            content = {
                RegistroScreen(RegistroViewModel(clienteRepository), navController)
            }
        )

        composable(
            route = Screen.Login.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            content = {
                LoginScreen(LoginViewModel(clienteRepository), navController)

            }
        )

        composable(
            route = Screen.Home.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            content = {
                HomeScreen(homeViewModel = HomeViewModel(productoRepository), navController = navController)
            },
        )
    }
}



