package com.taller.bdmoviles.model

// TODO (Día 2/3): Convertir esta clase en una Entidad de Base de Datos
data class Tarea(
    val id: Int = 0,
    val descripcion: String,
    val estaCompletada: Boolean = false
)