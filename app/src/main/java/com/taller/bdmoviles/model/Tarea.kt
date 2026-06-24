package com.taller.bdmoviles.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_tareas")
data class Tarea(
    // @PrimaryKey autoGenerate crea el ID automáticamente (1, 2, 3...)
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val descripcion: String,
    val estaCompletada: Boolean = false
)