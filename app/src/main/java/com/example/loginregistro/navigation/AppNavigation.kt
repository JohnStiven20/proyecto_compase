package com.example.loginregistro.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.loginregistro.data.network.ClienteApiService
import com.example.loginregistro.data.network.Retrofitinstance
import com.example.loginregistro.data.repositories.ClienteRepository
import com.example.loginregistro.data.repositories.ProductoRepositoty
import com.example.loginregistro.ui.home.HomeScreen
import com.example.loginregistro.ui.home.HomeViewModel
import com.example.loginregistro.ui.login.LoginScreen
import com.example.loginregistro.ui.login.LoginViewModel
import com.example.loginregistro.ui.registro.RegistroScreen
import com.example.loginregistro.ui.registro.RegistroViewModel


@Composable
fun AppNavigation(navController:  NavHostController) {



    val clienteApiService = Retrofitinstance.clienteApi

    val clienteRepository = ClienteRepository(apiService = clienteApiService)
    val productoRepositoty = ProductoRepositoty()

    NavHost(navController = navController, startDestination = Screen.Registro.route) {

        composable (
            route = Screen.Registro.route,
//            enterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
//            exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            RegistroScreen(RegistroViewModel(clienteRepository), navController)
        }

        composable(
            route = Screen.Login.route,
//            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
//            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            LoginScreen(LoginViewModel(clienteRepository), navController)
        }

        composable(
            route = Screen.Home.route,
//            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
//            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            HomeScreen(homeViewModel = HomeViewModel(productoRepositoty), navController = navController)
        }

    }
}



