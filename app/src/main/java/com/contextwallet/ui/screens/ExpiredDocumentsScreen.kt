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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contextwallet.ui.DocumentListViewModel
import com.contextwallet.ui.components.DocumentCard

import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ExpiredDocumentsScreen(
    onDocumentClick: (String) -> Unit,
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    val documents by viewModel.expiredDocuments.collectAsState()

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
                message = "No hay documentos caducados",
                description = "Los documentos que han pasado su fecha de fin aparecerán aquí",
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
