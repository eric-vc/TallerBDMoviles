package com.taller.bdmoviles.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taller.bdmoviles.ui.viewmodels.TareasViewModel

@Composable
fun PantallaCRUD(viewModel: TareasViewModel) {
    // La lista se actualizará sola cuando la BD cambie
    val listaTareas by viewModel.tareas.collectAsState()
    var nuevaTareaTexto by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Gestor de Tareas", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario de entrada
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = nuevaTareaTexto,
                onValueChange = { nuevaTareaTexto = it },
                modifier = Modifier.weight(1f),
                label = { Text("Nueva Tarea") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if(nuevaTareaTexto.isNotBlank()) {
                        viewModel.agregarTarea(nuevaTareaTexto)
                        nuevaTareaTexto = "" // Limpiar input
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Añadir")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        // Lista visual de elementos de la BD
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listaTareas) { tarea ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = tarea.descripcion)
                    IconButton(onClick = { viewModel.eliminarTarea(tarea) }) {
                        Text("❌")
                    }
                }
            }
        }
    }
}