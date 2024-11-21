package com.example.lista_tareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lista_tareas.ui.theme.Lista_TareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lista_TareasTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),

                    ) { innerPadding ->
                    AplicacionDeTareas(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color(0xFFF1CB9B))
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun AplicacionDeTareas(modifier: Modifier = Modifier) {
    var tareas by remember { mutableStateOf(tareasIniciales()) }

    Column(modifier = modifier.padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                "Tareas Pendientes",
                fontWeight = FontWeight.Bold

            )
        }

        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(3.dp), color = Color.Black
        )

        tareas.forEach { tarea ->
            if (!tarea.estaCompletada) {
                TarjetaDeTarea(tarea = tarea) { tareaActualizada ->
                    tareas =
                        tareas.map { if (it.id == tareaActualizada.id) tareaActualizada else it }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                "Tareas Completadas",
                fontWeight = FontWeight.Bold

            )
        }

        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(3.dp), color = Color.Black
        )

        tareas.forEach { tarea ->
            if (tarea.estaCompletada) {
                TarjetaDeTarea(tarea = tarea) { tareaActualizada ->
                    tareas =
                        tareas.map { if (it.id == tareaActualizada.id) tareaActualizada else it }
                }
            }
        }

    }
}

@Composable
fun TarjetaDeTarea(tarea: Tarea, onActualizarTarea: (Tarea) -> Unit) {
    var expandido by remember { mutableStateOf(false) }

    val textoDeOpcion = if (tarea.estaCompletada) {
        "Marcar como Pendiente"
    } else {
        "Marcar como Completada"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
        ) {

            BadgedBox(
                badge = {},
                modifier = Modifier
                    .background(color = tarea.prioridad.color, shape = MaterialTheme.shapes.small)
                    .size(12.dp)
            ) { }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                tarea.titulo,
                modifier = Modifier.weight(1f)
            )

            Box {
                IconButton(onClick = { expandido = !expandido }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menú")
                }
                DropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { expandido = false }
                ) {

                    DropdownMenuItem(
                        text = { Text(textoDeOpcion) },
                        onClick = {
                            onActualizarTarea(
                                Tarea(
                                    id = tarea.id,
                                    titulo = tarea.titulo,
                                    prioridad = tarea.prioridad,
                                    estaCompletada = !tarea.estaCompletada
                                )
                            )
                            expandido = false
                        }
                    )

                    Prioridad.values().forEach { prioridad ->

                        DropdownMenuItem(
                            text = { Text(prioridad.nombre) },
                            onClick = {
                                onActualizarTarea(
                                    Tarea(
                                        id = tarea.id,
                                        titulo = tarea.titulo,
                                        prioridad = prioridad,
                                        estaCompletada = tarea.estaCompletada
                                    )
                                )
                                expandido = false
                            }
                        )
                    }

                }
            }
        }
    }
}

fun tareasIniciales(): List<Tarea> {
    return listOf(
        Tarea(1, "Estudiar Jetpack Compose", false, Prioridad.ALTA),
        Tarea(2, "Estudiar Programacion", false, Prioridad.BAJA),
        Tarea(3, "Estudiar Acceso a Datos", true, Prioridad.MEDIA)
    )
}
