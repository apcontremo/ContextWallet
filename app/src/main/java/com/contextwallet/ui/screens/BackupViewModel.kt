package com.contextwallet.ui.screens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contextwallet.util.BackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(
    private val backupManager: BackupManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<BackupUiState>(BackupUiState.Idle)
    val uiState: StateFlow<BackupUiState> = _uiState.asStateFlow()

    fun exportData(uri: Uri) {
        viewModelScope.launch {
            _uiState.value = BackupUiState.Loading("Exportando datos...")
            val result = backupManager.exportData(uri)
            if (result.isSuccess) {
                _uiState.value = BackupUiState.Success("Copia de seguridad creada con éxito")
            } else {
                _uiState.value = BackupUiState.Error("Error al exportar: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun importData(uri: Uri) {
        viewModelScope.launch {
            _uiState.value = BackupUiState.Loading("Importando datos...")
            val result = backupManager.importData(uri)
            if (result.isSuccess) {
                _uiState.value = BackupUiState.Success("Datos importados con éxito. Se recomienda reiniciar la aplicación.")
            } else {
                _uiState.value = BackupUiState.Error("Error al importar: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = BackupUiState.Idle
    }
}

sealed class BackupUiState {
    object Idle : BackupUiState()
    data class Loading(val message: String) : BackupUiState()
    data class Success(val message: String) : BackupUiState()
    data class Error(val message: String) : BackupUiState()
}
