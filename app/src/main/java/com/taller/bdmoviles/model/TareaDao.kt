package com.taller.bdmoviles.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {
    // Flow permite que la lista en pantalla se actualice sola si la base de datos cambia
    @Query("SELECT * FROM tabla_tareas ORDER BY id DESC")
    fun obtenerTodasLasTareas(): Flow<List<Tarea>>

    // "suspend" obliga a esta función a ejecutarse en un hilo secundario (corrutina)
    @Insert
    suspend fun insertar(tarea: Tarea)

    @Delete
    suspend fun eliminar(tarea: Tarea)
}