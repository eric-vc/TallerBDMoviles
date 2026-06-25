package com.taller.bdmoviles.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.taller.bdmoviles.model.AppDatabase
import com.taller.bdmoviles.model.FirestoreRepository
import com.taller.bdmoviles.model.Tarea
import com.taller.bdmoviles.model.TareaCloud
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Notar que heredamos de AndroidViewModel para tener acceso al Application (Contexto)
class TareasViewModel(application: Application) : AndroidViewModel(application) {

    // 1. Inicializamos la base de datos llamando a nuestro Singleton
    private val tareaDao = AppDatabase.getDatabase(application).tareaDao()

    // 2. Lectura Asíncrona (Reemplaza la lista temporal)
    val tareas: StateFlow<List<Tarea>> = tareaDao.obtenerTodasLasTareas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 3. Inserción Segura
    fun agregarTarea(descripcion: String) {
        viewModelScope.launch {
            tareaDao.insertar(Tarea(descripcion = descripcion))
        }
    }

    // 4. Eliminación Segura
    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            tareaDao.eliminar(tarea)
        }
    }

    // --- CÓDIGO DEL DÍA 3 (NUBE) ---
    private val firestoreRepository = FirestoreRepository()

    // Tubería reactiva conectada directamente a los servidores de Google
    val tareasNube: StateFlow<List<TareaCloud>> = firestoreRepository.obtenerTareasDeLaNube()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarTareaNube(descripcion: String) {
        viewModelScope.launch {
            try {
                firestoreRepository.insertarEnNube(descripcion)
            } catch (e: Exception) {
                e.printStackTrace() // Manejo elemental de errores de red
            }
        }
    }

    fun eliminarTareaNube(id: String) {
        viewModelScope.launch {
            try {
                firestoreRepository.eliminarDeNube(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}