package com.taller.bdmoviles.model

// Modelo de datos optimizado para Firebase Firestore
data class TareaCloud(
    // Firebase genera IDs alfanuméricos de forma aleatoria (ej: "h7Gk9pLz2")
    val id: String = "",
    val descripcion: String = "",
    val estaCompletada: Boolean = false
)