package com.example.loginregistro.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

    val clienteRepository = ClienteRepository()
    val productoRepositoty = ProductoRepositoty()


    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable (
            route = Screen.Registro.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) { hola ->
            hola.destination.arguments.size
            RegistroScreen(RegistroViewModel(clienteRepository), navController)
        }

        composable(
            route = Screen.Login.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            LoginScreen(LoginViewModel(), navController)
        }

        composable(
            route = Screen.Home.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            HomeScreen(homeViewModel = HomeViewModel(), navController = navController)
        }

    }
}



