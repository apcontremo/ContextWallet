package com.contextwallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contextwallet.ui.DocumentListViewModel
import com.contextwallet.ui.components.DocumentCard

import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun InactiveDocumentsScreen(
    onDocumentClick: (String) -> Unit,
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    val documents by viewModel.inactiveDocuments.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 24.dp)
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.55f),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        if (documents.isEmpty()) {
            EmptyState(
                message = "No hay documentos inactivos",
                description = "Los documentos que aún no son válidos o están fuera de su rango de ubicación aparecerán aquí",
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
