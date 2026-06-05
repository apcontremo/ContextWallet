package com.contextwallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contextwallet.ui.DocumentListViewModel
import com.contextwallet.ui.components.DocumentCard

import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ActiveDocumentsScreen(
    onDocumentClick: (String) -> Unit,
    onCreateClick: () -> Unit,
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    val documents by viewModel.activeDocuments.collectAsState()

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                containerColor = com.contextwallet.ui.theme.FabBlue,
                contentColor = androidx.compose.ui.graphics.Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir documento")
            }
        }
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
            if (documents.isEmpty()) {
                EmptyState(
                    message = "No hay documentos activos",
                    description = "Los documentos aparecerán aquí cuando estén dentro de su período de validez y rango de ubicación",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(documents, key = { it.document.id }) { document ->
                        DocumentCard(
                            document = document,
                            onClick = { onDocumentClick(document.document.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyState(
    message: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge.copy(
                shadow = androidx.compose.ui.graphics.Shadow(
                    color = androidx.compose.ui.graphics.Color.Black,
                    blurRadius = 4f
                )
            ),
            textAlign = TextAlign.Center,
            color = androidx.compose.ui.graphics.Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(
                shadow = androidx.compose.ui.graphics.Shadow(
                    color = androidx.compose.ui.graphics.Color.Black,
                    blurRadius = 2f
                )
            ),
            textAlign = TextAlign.Center,
            color = androidx.compose.ui.graphics.Color.White
        )
    }
}
