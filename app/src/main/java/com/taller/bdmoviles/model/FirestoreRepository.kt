package com.taller.bdmoviles.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    // Inicializa la instancia oficial del SDK de Firestore
    private val firestore = FirebaseFirestore.getInstance()
    private val coleccionTareas = firestore.collection("tareas_nube")

    // Escucha cambios en tiempo real en la nube y los transforma en un Flow de Kotlin
    fun obtenerTareasDeLaNube(): Flow<List<TareaCloud>> = callbackFlow {
        val listener = coleccionTareas.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                // Mapea los documentos JSON de la nube directamente a objetos Kotlin TareaCloud
                val lista = snapshot.documents.map { doc ->
                    TareaCloud(
                        id = doc.id,
                        descripcion = doc.getString("descripcion") ?: "",
                        estaCompletada = doc.getBoolean("estaCompletada") ?: false
                    )
                }
                trySend(lista) // Inyecta la lista actualizada al flujo de la UI
            }
        }
        // Si la pantalla se cierra, apaga el escuchador de Firebase para no gastar datos de internet
        awaitClose { listener.remove() }
    }

    // Inserción Asíncrona en la Nube usando Corrutinas (.await())
    suspend fun insertarEnNube(descripcion: String) {
        val datos = mapOf(
            "descripcion" to descripcion,
            "estaCompletada" to false
        )
        coleccionTareas.add(datos).await()
    }

    // Eliminación por ID de documento único
    suspend fun eliminarDeNube(idDocumento: String) {
        coleccionTareas.document(idDocumento).delete().await()
    }
}