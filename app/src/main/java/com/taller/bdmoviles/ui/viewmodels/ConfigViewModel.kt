package com.taller.bdmoviles.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConfigViewModel : ViewModel() {

    // TODO (Día 1): Conectar este StateFlow con la lectura asíncrona de DataStore
    private val _modoOscuro = MutableStateFlow(false)
    val modoOscuro: StateFlow<Boolean> = _modoOscuro

    fun guardarPreferenciaModoOscuro(activado: Boolean) {
        // TODO (Día 1): Escribir el nuevo valor en el archivo físico del dispositivo
        _modoOscuro.value = activado
    }
}