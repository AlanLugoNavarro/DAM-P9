package com.example.practica9v2.presentation.presentacion.calculadora

import android.widget.EditText
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.example.practica9v2.presentation.theme.FondoP
import com.example.practica9v2.presentation.theme.LetrasD
import com.example.practica9v2.presentation.theme.LetrasP
import net.objecthunter.exp4j.ExpressionBuilder




@Composable
fun PantallaCalculadora(navController: NavHostController) {
    var resultado by remember { mutableStateOf("0") } // numero
    var num1: Int = 0;
    var num2: Int = 0;
    var operacion: String = "";

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < -100) {
                        navController.navigate("bloc")
                    }
                }
            }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF70B171)), // 📌 Fondo verde como en la imagen
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // 🖥 Pantalla de la calculadora
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(FondoP)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = resultado,
                    color = if(resultado == "0" || resultado == "0.0") LetrasD else LetrasP,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            /*
              val botones = listOf(
               "7", "8", "9", "C",
               "4", "5", "6", "+", "-",
               "1", "2", "3", "x", "÷",
               "0", ".", "="
           )
           * */

            // 🔢 Botones de la calculadora en un Grid
            val botones = listOf(
                listOf("7", "8", "9", "C"),
                listOf("4", "5", "6", "+", "-"),
                listOf("1", "2", "3", "×", "÷"),
                listOf("0", ".", "=")
            )


            Column {
                botones.forEach { fila ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        fila.forEach { boton ->
                            val ancho = if(boton == "C" || boton == "=" || boton == "0") 60.dp else 30.dp
                            BotonCalculadora(texto = boton, ancho) {
                                resultado = procesarEntrada(resultado, boton)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BotonCalculadora(texto: String, ancho: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(ancho)
            .height(30.dp)
            .background(FondoP)
            .border(2.dp, Color.Black)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = texto, color = LetrasP, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

// 📌 Función para procesar entradas de la calculadora
fun procesarEntrada(actual: String, nueva: String): String {
    return when (nueva) {
        "C" -> "0" // 🧹 Borra todo
        "=" -> try {
            evaluarExpresion(actual) // 📌 Ahora evalúa la operación completa
        } catch (e: Exception) {
            "Error"
        }
        else -> {
            if (actual == "0") nueva else actual + nueva // Concatenar números y operadores
        }
    }
}

// 📌 Función para evaluar la expresión matemática
fun evaluarExpresion(expresion: String): String {
    return try {
        val resultado = ExpressionBuilder(expresion.replace("×", "*").replace("÷", "/")).build().evaluate()
        resultado.toString()
    } catch (e: Exception) {
        "Error"
    }
}

