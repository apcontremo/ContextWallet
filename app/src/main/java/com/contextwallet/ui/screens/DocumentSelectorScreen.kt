package com.contextwallet.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contextwallet.R
import com.contextwallet.data.local.entity.Document
import com.contextwallet.domain.model.DocumentState
import com.contextwallet.domain.model.DocumentWithState
import com.contextwallet.ui.DocumentListViewModel
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.foundation.shape.RoundedCornerShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentSelectorScreen(
    sharedFileUri: String,
    onCreateNew: () -> Unit,
    onReplace: (String) -> Unit,
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val documents by viewModel.allDocuments.collectAsState()
    var isProcessing by remember { mutableStateOf(false) }
    
    val uri = remember(sharedFileUri) { Uri.parse(Uri.decode(sharedFileUri)) }
    val fileName = remember(uri) { uri.lastPathSegment ?: "Documento" }
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("¿Qué deseas hacer?") }
            )
        },
        containerColor = androidx.compose.ui.graphics.Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 8.dp, vertical = 24.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Has compartido: $fileName",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    "Puedes crear un nuevo documento con este archivo o reemplazar el archivo de un documento existente.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Option 1: Create New
                OutlinedCard(
                    onClick = { if (!isProcessing) onCreateNew() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isProcessing
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Crear nuevo documento", style = MaterialTheme.typography.titleMedium)
                            Text(
                                "Registrar un nuevo documento desde cero",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                Divider()
                
                Text(
                    "O reemplazar en un documento existente:",
                    style = MaterialTheme.typography.titleSmall
                )
                
                if (documents.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No hay documentos guardados",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(documents, key = { it.document.id }) { docWithState ->
                            val doc = docWithState.document
                            OutlinedCard(
                                onClick = {
                                    if (!isProcessing) {
                                        onReplace(doc.id)
                                    }
                                },
                                enabled = !isProcessing,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        when {
                                            doc.fileType.contains("pdf", true) -> Icons.Default.Description
                                            else -> Icons.Default.Description
                                        },
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            doc.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            "Válido hasta ${dateFormat.format(Date(doc.endDate))}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        // Show state badge
                                        val stateText = when (docWithState.state) {
                                            DocumentState.ACTIVE -> stringResource(R.string.state_active)
                                            DocumentState.INACTIVE_NOT_STARTED -> stringResource(R.string.state_inactive_not_started)
                                            DocumentState.INACTIVE_OUT_OF_RANGE -> stringResource(R.string.state_inactive_out_of_range)
                                            DocumentState.EXPIRED -> stringResource(R.string.state_expired)
                                        }
                                        val stateColor = when (docWithState.state) {
                                            DocumentState.ACTIVE -> MaterialTheme.colorScheme.primary
                                            DocumentState.INACTIVE_NOT_STARTED,
                                            DocumentState.INACTIVE_OUT_OF_RANGE -> MaterialTheme.colorScheme.tertiary
                                            DocumentState.EXPIRED -> MaterialTheme.colorScheme.error
                                        }
                                        Text(
                                            stateText,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = stateColor
                                        )
                                    }
                                    Icon(
                                        Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Loading overlay
        if (isProcessing) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
