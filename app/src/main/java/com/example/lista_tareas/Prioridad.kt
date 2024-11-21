package com.example.lista_tareas

import androidx.compose.ui.graphics.Color

enum class Prioridad(val nombre: String, val color: Color) {
    ALTA("Alta", Color.Red),
    MEDIA("Media", Color.Yellow),
    BAJA("Baja", Color.Green)
}