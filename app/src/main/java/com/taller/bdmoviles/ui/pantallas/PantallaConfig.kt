package com.taller.bdmoviles.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taller.bdmoviles.ui.viewmodels.ConfigViewModel

@Composable
fun PantallaConfig(viewModel: ConfigViewModel) {
    // Observamos el estado del ViewModel (que luego vendrá de DataStore)
    val modoOscuro by viewModel.modoOscuro.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Configuración de la App", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Habilitar Modo Oscuro", style = MaterialTheme.typography.bodyLarge)

            Switch(
                checked = modoOscuro,
                onCheckedChange = { nuevoEstado ->
                    // El alumno programará la persistencia aquí a través del ViewModel
                    viewModel.guardarPreferenciaModoOscuro(nuevoEstado)
                }
            )
        }
    }
}