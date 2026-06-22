package com.taller.bdmoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// Importes de las pantallas y ViewModels que crearemos en los paquetes
import com.taller.bdmoviles.ui.pantallas.PantallaConfig
import com.taller.bdmoviles.ui.pantallas.PantallaCRUD
import com.taller.bdmoviles.ui.theme.TallerBDMovilesTheme // Ajuste esto si su tema tiene otro nombre
import com.taller.bdmoviles.ui.viewmodels.ConfigViewModel
import com.taller.bdmoviles.ui.viewmodels.TareasViewModel

class MainActivity : ComponentActivity() {

    // Instanciamos los ViewModels que mantendrán vivos los datos al rotar la pantalla
    private val configViewModel: ConfigViewModel by viewModels()
    private val tareasViewModel: TareasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TallerBDMovilesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EnrutadorApp(configViewModel, tareasViewModel)
                }
            }
        }
    }
}

@Composable
fun EnrutadorApp(configVM: ConfigViewModel, tareasVM: TareasViewModel) {
    // Un sistema de navegación muy sencillo para no requerir librerías externas
    var pantallaActual by remember { mutableStateOf("Menu") }

    when (pantallaActual) {
        "Menu" -> MenuPrincipal(
            onNavegarConfig = { pantallaActual = "Config" },
            onNavegarCRUD = { pantallaActual = "CRUD" }
        )
        "Config" -> {
            Column(modifier = Modifier.fillMaxSize()) {
                BotonRegresar { pantallaActual = "Menu" }
                PantallaConfig(viewModel = configVM)
            }
        }
        "CRUD" -> {
            Column(modifier = Modifier.fillMaxSize()) {
                BotonRegresar { pantallaActual = "Menu" }
                PantallaCRUD(viewModel = tareasVM)
            }
        }
    }
}

@Composable
fun MenuPrincipal(onNavegarConfig: () -> Unit, onNavegarCRUD: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Taller: Bases de Datos Móviles",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onNavegarConfig,
            modifier = Modifier.fillMaxWidth(0.75f).height(50.dp)
        ) {
            Text("Día 1: Clave-Valor")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onNavegarCRUD,
            modifier = Modifier.fillMaxWidth(0.75f).height(50.dp)
        ) {
            Text("Día 2 y 3: Relacional / NoSQL")
        }
    }
}

@Composable
fun BotonRegresar(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("⬅ Volver al Menú Principal")
    }
}