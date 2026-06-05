package com.contextwallet.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupScreen(
    onNavigateBack: () -> Unit,
    viewModel: BackupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/zip"),
        onResult = { uri -> uri?.let { viewModel.exportData(it) } }
    )
    
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri -> uri?.let { viewModel.importData(it) } }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Copia de seguridad") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Backup,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Exporta tus documentos y datos para migrarlos a otro dispositivo o guardarlos como respaldo.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Button(
                onClick = { exportLauncher.launch("ContextWallet_Backup.zip") },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is BackupUiState.Loading
            ) {
                Icon(Icons.Default.FileUpload, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Exportar Datos (ZIP)")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = { importLauncher.launch(arrayOf("application/zip")) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is BackupUiState.Loading
            ) {
                Icon(Icons.Default.Restore, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Importar Datos (ZIP)")
            }
            
            if (uiState is BackupUiState.Loading) {
                Spacer(modifier = Modifier.height(32.dp))
                CircularProgressIndicator()
                Text(
                    text = (uiState as BackupUiState.Loading).message,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    // Success/Error Dialogs
    when (val state = uiState) {
        is BackupUiState.Success -> {
            AlertDialog(
                onDismissRequest = { viewModel.resetState() },
                confirmButton = {
                    TextButton(onClick = { viewModel.resetState() }) {
                        Text("OK")
                    }
                },
                title = { Text("Éxito") },
                text = { Text(state.message) }
            )
        }
        is BackupUiState.Error -> {
            AlertDialog(
                onDismissRequest = { viewModel.resetState() },
                confirmButton = {
                    TextButton(onClick = { viewModel.resetState() }) {
                        Text("OK")
                    }
                },
                title = { Text("Error") },
                text = { Text(state.message) }
            )
        }
        else -> {}
    }
}
