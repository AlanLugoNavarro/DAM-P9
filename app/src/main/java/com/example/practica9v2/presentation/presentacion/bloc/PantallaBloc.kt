package com.example.practica9v2.presentation.presentacion.bloc

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.practica9v2.presentation.theme.FondoI
import com.example.practica9v2.presentation.theme.FondoO
import com.example.practica9v2.presentation.theme.FondoP

@Composable
fun PantallaBloc(navController: NavHostController) {
    var paths by remember { mutableStateOf(listOf<Triple<Path, Color, Float>>()) } // Color y grosor almacenado
    var currentPath by remember { mutableStateOf(Path()) }
    var currentColor by remember { mutableStateOf(Color.Black) }
    var currentStrokeWidth by remember { mutableStateOf(8f) }
    var herramientaSeleccionada by remember { mutableStateOf("🖊") }

    val paint = Paint().apply {
        color = currentColor
        strokeWidth = currentStrokeWidth
        isAntiAlias = true
        style = PaintingStyle.Stroke
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < -100) {
                        navController.navigate("relojd")
                    }
                }
            }
    ){
        Row(modifier = Modifier.background(FondoO)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(FondoP)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { change ->
                                currentPath = Path().apply { moveTo(change.x, change.y) }
                            },
                            onDrag = { change, _ ->
                                currentPath = Path().apply {
                                    addPath(currentPath)
                                    lineTo(change.position.x, change.position.y)
                                }
                            },
                            onDragEnd = {
                                val nuevoPaint = Paint().apply {
                                    color = currentColor
                                    strokeWidth = currentStrokeWidth
                                    isAntiAlias = true
                                    style = PaintingStyle.Stroke
                                }

                                paths = paths + Triple(Path().apply { addPath(currentPath) }, currentColor, currentStrokeWidth)

                                currentPath = Path()
                            }


                        )
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    paths.forEach { (path, color, strokeWidth) ->
                        drawPath(path = path, color = color, style = Stroke(strokeWidth))
                    }
                    drawPath(path = currentPath, color = currentColor, style = Stroke(currentStrokeWidth))
                }
            }
            Column(
                modifier = Modifier
                    .width(50.dp)
                    .padding(start = 10.dp, top = 5.dp)
                    .background(FondoO),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    herramientaSeleccionada = "🖊"
                    currentColor = Color.Black
                    currentStrokeWidth = 4f
                }, modifier = Modifier.width(30.dp).height(80.dp).border(2.dp, Color.Black).background(FondoO),
                    shape = RectangleShape, contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = if (herramientaSeleccionada == "🖊") FondoI else FondoO)) {
                    Text("🖊", Modifier.background(if(herramientaSeleccionada == "🖊") FondoI else FondoO))
                }

                Button(onClick = {
                    herramientaSeleccionada = "🧽"
                    currentColor = FondoP
                    currentStrokeWidth = 20f // Goma más gruesa
                }, modifier = Modifier.width(30.dp).height(80.dp).border(2.dp, Color.Black).background(FondoO),
                    shape = RectangleShape, contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = if (herramientaSeleccionada == "🧽") FondoI else FondoO)) {
                    Text("🧽", Modifier.background(if(herramientaSeleccionada == "🧽") FondoI else FondoO))
                }
            }
        }
    }
}

