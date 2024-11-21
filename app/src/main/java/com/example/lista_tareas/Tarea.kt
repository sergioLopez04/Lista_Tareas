package com.example.lista_tareas

data class Tarea(

    val id: Int,
    var titulo: String,
    var estaCompletada: Boolean,
    var prioridad: Prioridad

)
