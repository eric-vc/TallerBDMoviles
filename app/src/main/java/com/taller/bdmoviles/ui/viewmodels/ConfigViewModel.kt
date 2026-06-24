package com.taller.bdmoviles.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// 1. Declarar el archivo físico
val Context.dataStore by preferencesDataStore(name = "configuraciones")

// SOLUCIÓN AL ERROR ROJO: Agregar 'private val' en el constructor
class ConfigViewModel(private val application: Application) : AndroidViewModel(application) {

    // SOLUCIÓN A LA ADVERTENCIA AMARILLA: Usar camelCase
    private val modoOscuroKey = booleanPreferencesKey("modo_oscuro")

    // 2. Lectura Asíncrona
    val modoOscuro: StateFlow<Boolean> = application.dataStore.data
        .map { preferences ->
            preferences[modoOscuroKey] ?: false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false
        )

    // 3. Escritura Segura
    fun guardarPreferenciaModoOscuro(activado: Boolean) {
        viewModelScope.launch {
            application.dataStore.edit { preferences ->
                preferences[modoOscuroKey] = activado
            }
        }
    }
}