package com.example.loginregistro.ui.componentes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginregistro.ui.home.HomeScreen
import com.example.loginregistro.ui.home.HomeViewModel
import com.example.loginregistro.ui.login.LoginScreen
import com.example.loginregistro.ui.login.LoginViewModel
import com.example.loginregistro.ui.registro.RegistroScreen
import com.example.loginregistro.ui.registro.RegistroViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "registro") {
        composable(
            route = "registro",
            enterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            RegistroScreen(RegistroViewModel(), navController)
        }

        composable(
            route = "login",
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            LoginScreen(LoginViewModel(), navController)
        }

        composable(
            route = "Home",
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            HomeScreen(homeViewModel = HomeViewModel())
        }
    }
}
