package com.contextwallet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contextwallet.data.repository.DocumentRepository
import com.contextwallet.domain.model.DocumentState
import com.contextwallet.domain.model.DocumentWithState
import com.contextwallet.util.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentListViewModel @Inject constructor(
    private val repository: DocumentRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {
    
    private val _currentLocation = MutableStateFlow<Pair<Double, Double>?>(null)
    private val _currentCountryCode = MutableStateFlow<String?>(null)
    
    val allDocuments: StateFlow<List<DocumentWithState>> = combine(
        _currentLocation,
        _currentCountryCode,
        flow { 
            while(true) {
                emit(System.currentTimeMillis())
                kotlinx.coroutines.delay(60000) // Update minute by minute
            }
        }
    ) { loc, country, time -> Triple(loc, country, time) }
        .flatMapLatest { (loc, country, time) ->
            repository.getAllDocumentsWithState(
                time,
                loc?.first,
                loc?.second,
                country
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val activeDocuments: StateFlow<List<DocumentWithState>> = allDocuments
        .map { list -> list.filter { it.state == DocumentState.ACTIVE } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val inactiveDocuments: StateFlow<List<DocumentWithState>> = allDocuments
        .map { list -> 
            list.filter { 
                it.state == DocumentState.INACTIVE_NOT_STARTED || 
                it.state == DocumentState.INACTIVE_OUT_OF_RANGE 
            } 
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val expiredDocuments: StateFlow<List<DocumentWithState>> = allDocuments
        .map { list -> list.filter { it.state == DocumentState.EXPIRED } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    init {
        refreshLocation()
    }
    
    fun refreshLocation() {
        viewModelScope.launch {
            val location = locationProvider.getCurrentLocation()
            if (location != null) {
                _currentLocation.value = Pair(location.latitude, location.longitude)
                // Get country code in background
                launch(kotlinx.coroutines.Dispatchers.IO) {
                    val country = locationProvider.getCountryCode(location.latitude, location.longitude)
                    _currentCountryCode.value = country
                }
            }
        }
    }
    
    fun deleteDocument(documentId: String) {
        viewModelScope.launch {
            val documents = activeDocuments.value + inactiveDocuments.value + expiredDocuments.value
            val document = documents.find { it.document.id == documentId }
            if (document != null) {
                repository.deleteDocument(document.document)
            }
        }
    }
}
