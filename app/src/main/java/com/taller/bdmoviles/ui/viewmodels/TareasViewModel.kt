package com.taller.bdmoviles.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.taller.bdmoviles.model.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TareasViewModel : ViewModel() {

    // TODO (Día 2/3): Reemplazar esta lista en memoria por una consulta (Query) reactiva a la Base de Datos
    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> = _tareas

    fun agregarTarea(descripcion: String) {
        // TODO (Día 2/3): Ejecutar un DAO.insert() o Realm.insert() aquí
        val nuevaTarea = Tarea(id = _tareas.value.size + 1, descripcion = descripcion)
        _tareas.value = _tareas.value + nuevaTarea
    }

    fun eliminarTarea(tarea: Tarea) {
        // TODO (Día 2/3): Ejecutar un DAO.delete() o Realm.delete() aquí
        _tareas.value = _tareas.value.filter { it.id != tarea.id }
    }
}