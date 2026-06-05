package com.contextwallet.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contextwallet.R
import com.contextwallet.domain.model.DocumentState
import com.contextwallet.ui.components.StateBadge
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentDetailScreen(
    documentId: String,
    onNavigateBack: () -> Unit,
    onEditDocument: (String) -> Unit,
    viewModel: DocumentDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val documentWithState by viewModel.documentState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(documentId) {
        viewModel.loadDocument(documentId)
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("¿Eliminar documento?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteDocument {
                            showDeleteDialog = false
                            onNavigateBack()
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Documento") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditDocument(documentId) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar"
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) { padding ->
        documentWithState?.let { doc ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Name and Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = doc.document.name,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.weight(1f)
                    )
                    StateBadge(state = doc.state)
                }

                // Description (only if not active)
                if (doc.state != DocumentState.ACTIVE) {
                    val stateDesc = when (doc.state) {
                        DocumentState.INACTIVE_NOT_STARTED -> {
                            val dateStr = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(Date(doc.document.startDate))
                            stringResource(R.string.state_inactive_not_started_date_desc, dateStr)
                        }
                        DocumentState.INACTIVE_OUT_OF_RANGE -> {
                            val dist = doc.distanceToNearestPoint
                            if (dist != null) stringResource(R.string.state_inactive_out_of_range_km_desc, dist)
                            else stringResource(R.string.state_inactive_out_of_range_desc)
                        }
                        DocumentState.EXPIRED -> stringResource(R.string.state_expired_desc)
                        else -> ""
                    }
                    Text(
                        text = stateDesc,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Divider()

                // Action Button to Consult Document
                Button(
                    onClick = { viewModel.openDocument(context) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = doc.document.fileUri.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(Icons.Default.Visibility, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Consultar Documento")
                }

                Divider()

                // Dates
                val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailItem(
                        icon = Icons.Default.CalendarToday,
                        label = "Inicio",
                        value = dateFormat.format(Date(doc.document.startDate))
                    )
                    DetailItem(
                        icon = Icons.Default.Event,
                        label = "Fin",
                        value = dateFormat.format(Date(doc.document.endDate))
                    )
                }

                Divider()

                // Points
                Text(
                    text = "Áreas de Validez",
                    style = MaterialTheme.typography.titleLarge
                )
                
                if (doc.document.globalMode) {
                    Text("Este documento es válido en cualquier lugar (Modo Global).")
                } else if (!doc.document.countryCode.isNullOrEmpty()) {
                    Text("Válido en todo el país: ${doc.document.countryCode}")
                } else {
                    if (doc.points.isEmpty()) {
                        Text("No hay áreas de validez definidas.")
                    } else {
                        doc.points.forEach { point ->
                            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = point.label ?: "Área sin nombre",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    if (!point.address.isNullOrBlank()) {
                                        Text(
                                            text = point.address,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    } else {
                                        Text(
                                            text = "Lat: ${point.latitude}, Lon: ${point.longitude}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Text(
                                        text = "Radio: ${point.radiusKm} km",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextButton(
                                        onClick = {
                                            try {
                                                val uri = Uri.parse("geo:${point.latitude},${point.longitude}?q=${point.latitude},${point.longitude}(${point.label})")
                                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                                context.startActivity(intent)
                                            } catch (e: Exception) {
                                                // Handle map not found
                                            }
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.align(androidx.compose.ui.Alignment.End)
                                    ) {
                                        Icon(
                                            Icons.Default.Map, 
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Cómo llegar", style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // More details or actions could go here
            }
        } ?: Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
        }
    }
}

@Composable
fun DetailItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium)
            Text(text = value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
