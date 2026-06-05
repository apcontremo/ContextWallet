package com.contextwallet.ui.screens

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contextwallet.data.repository.DocumentRepository
import com.contextwallet.domain.model.DocumentWithState
import com.contextwallet.util.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentDetailViewModel @Inject constructor(
    private val repository: DocumentRepository,
    private val fileManager: FileManager,
    private val locationProvider: com.contextwallet.util.LocationProvider
) : ViewModel() {

    private val _documentState = MutableStateFlow<DocumentWithState?>(null)
    val documentState = _documentState.asStateFlow()

    fun loadDocument(documentId: String) {
        viewModelScope.launch {
            val location = locationProvider.getLastKnownLocation()
            var countryCode: String? = null
            
            if (location != null) {
                // Try to get country code if we have location
                 try {
                     countryCode = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                         locationProvider.getCountryCode(location.latitude, location.longitude)
                     }
                 } catch (e: Exception) {
                     // Ignore error
                 }
            }

            val document = repository.getDocumentWithState(
                documentId = documentId,
                currentTime = System.currentTimeMillis(),
                userLatitude = location?.latitude,
                userLongitude = location?.longitude,
                userCountryCode = countryCode
            )
            _documentState.value = document
        }
    }

    fun deleteDocument(onSuccess: () -> Unit) {
        val document = _documentState.value?.document ?: return
        viewModelScope.launch {
            // Delete file first
            if (document.fileUri.isNotEmpty()) {
                fileManager.deleteFile(document.fileUri)
            }
            repository.deleteDocument(document)
            onSuccess()
        }
    }

    fun openDocument(context: Context) {
        val doc = _documentState.value?.document ?: return
        if (doc.fileUri.isEmpty()) return

        val uri = fileManager.getShareableUri(doc.fileUri) ?: return
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, doc.fileType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        
        try {
            // Direct open is faster and less confusing if a default is set
            context.startActivity(intent)
        } catch (e: Exception) {
            // If direct fails, try with chooser as fall back
            try {
                context.startActivity(Intent.createChooser(intent, "Abrir documento"))
            } catch (e2: Exception) {
                // No apps available at all
            }
        }
    }
}
