package com.example.practica9v2.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica9v2.presentation.presentacion.bloc.PantallaBloc
import com.example.practica9v2.presentation.presentacion.calculadora.PantallaCalculadora
import com.example.practica9v2.presentation.presentacion.pasos.PantallaPasos
import com.example.practica9v2.presentation.presentacion.relojd.PantallaRelojD

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "relojd") {
        composable("pasos") {
            PantallaPasos(navController)
        }
        composable("calculadora") {
            PantallaCalculadora(navController)
        }

        composable("relojd"){
            PantallaRelojD(navController)
        }

        composable("bloc"){
            PantallaBloc(navController)
        }
    }
}