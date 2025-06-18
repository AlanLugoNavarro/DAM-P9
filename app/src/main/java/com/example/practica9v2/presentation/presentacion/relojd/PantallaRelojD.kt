package com.example.practica9v2.presentation.presentacion.relojd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.practica9v2.R
import com.example.practica9v2.presentation.theme.Black
import com.example.practica9v2.presentation.theme.FondoI
import com.example.practica9v2.presentation.theme.FondoP
import com.example.practica9v2.presentation.theme.Gray
import com.example.practica9v2.presentation.theme.LetrasP
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PantallaRelojD(navController: NavHostController) {
    val horaActual by remember {
        mutableStateOf(
            LocalDateTime.now(ZoneId.of("America/Mexico_City"))
                .format(DateTimeFormatter.ofPattern("HH:mm"))
        )

    }

    var fondoColor by remember { mutableStateOf(FondoP) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = fondoColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        fondoColor = FondoI
                        tryAwaitRelease()
                        fondoColor = FondoP
                    }
                )
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < -100) {
                        navController.navigate("calculadora")
                    }
                }
            }


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = horaActual,
                color = LetrasP,
                fontSize = 60.sp
            )
        }

        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.pikachu),
                contentDescription = "Pikachu",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomStart)
                    .padding(end = 110.dp),

            )
        }
    }
}