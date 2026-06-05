package com.contextwallet.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.location.Geocoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contextwallet.data.local.entity.Document
import com.contextwallet.data.local.entity.DocumentPoint
import com.contextwallet.data.repository.DocumentRepository
import com.contextwallet.domain.model.DocumentWithState
import com.contextwallet.util.FileManager
import com.contextwallet.util.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class SearchResult(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val fullAddress: String
)

@HiltViewModel
class DocumentCreateViewModel @Inject constructor(
    private val repository: DocumentRepository,
    private val geocodingRepository: com.contextwallet.data.repository.GeocodingRepository,
    private val fileManager: FileManager,
    private val locationProvider: LocationProvider,
    private val debugLogger: com.contextwallet.util.DebugLogger,
    @ApplicationContext private val context: Context
) : ViewModel() {
    
    private val _existingDocument = MutableStateFlow<Document?>(null)
    val existingDocument = _existingDocument.asStateFlow()
    
    private val _existingPoints = MutableStateFlow<List<DocumentPoint>>(emptyList<DocumentPoint>())
    val existingPoints = _existingPoints.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList<SearchResult>())
    val searchResults = _searchResults.asStateFlow()
    
    private var searchJob: kotlinx.coroutines.Job? = null

    fun searchLocation(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList<SearchResult>()
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            debugLogger.log("CreateDoc", "Starting search for: $query")
            kotlinx.coroutines.delay(400) // Lower debounce to 400ms
            try {
                // Use OpenStreetMap Nominatim
                val results = geocodingRepository.searchLocation(query)
                debugLogger.log("CreateDoc", "Found ${results.size} results for: $query")
                _searchResults.value = results
            } catch (e: Exception) {
                debugLogger.log("CreateDoc", "Search error for $query: ${e.message}")
                _searchResults.value = emptyList()
            }
        }
    }

    fun loadDocument(documentId: String) {
        viewModelScope.launch {
            val location = locationProvider.getLastKnownLocation()
            var countryCode: String? = null
            
            if (location != null) {
                try {
                     countryCode = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                         locationProvider.getCountryCode(location.latitude, location.longitude)
                     }
                } catch (e: Exception) {}
            }

            val docWithState = repository.getDocumentWithState(
                documentId = documentId,
                currentTime = System.currentTimeMillis(),
                userLatitude = location?.latitude,
                userLongitude = location?.longitude,
                userCountryCode = countryCode
            )
            _existingDocument.value = docWithState?.document
            _existingPoints.value = docWithState?.points ?: emptyList<DocumentPoint>()
        }
    }

    fun saveDocument(
        documentId: String?,
        name: String,
        startDate: Long,
        endDate: Long,
        fileUri: Uri?,
        fileType: String,
        radiusKm: Double,
        globalMode: Boolean,
        countryCode: String?,
        validityAreas: List<DocumentPoint>, // Replaces single lat/lon/label
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Determine if we are updating or creating. Treat empty string OR literal template as null.
                val safeDocumentId = if (documentId.isNullOrBlank() || documentId == "{documentId}") null else documentId
                
                debugLogger.log("CreateDoc", "Starting save. docId: $safeDocumentId, name: $name")
                
                // If it's a new file (different URI than existing or no existing), copy it
                val currentFileUri = _existingDocument.value?.fileUri ?: ""
                val fileUriString = fileUri?.toString() ?: ""
                val hasNewFile = fileUri != null && fileUriString != currentFileUri && fileUriString.isNotBlank() && fileUriString != "{sharedUri}"
                
                val internalPath = if (hasNewFile) {
                    fileManager.copyToInternalStorage(fileUri!!, fileType)
                } else {
                    currentFileUri
                }
                
                val id = safeDocumentId ?: UUID.randomUUID().toString()
                
                val document = Document(
                    id = id,
                    name = name,
                    startDate = startDate,
                    endDate = endDate,
                    fileUri = internalPath,
                    fileType = fileType,
                    defaultRadiusKm = radiusKm,
                    globalMode = globalMode,
                    countryCode = countryCode
                )
                
                // Preparing points with correct document ID
                val finalPoints = validityAreas.map { point ->
                   point.copy(documentId = id)
                }
                
                debugLogger.log("CreateDoc", "Saving document $id with ${finalPoints.size} points")
                finalPoints.forEach { p ->
                    debugLogger.log("CreateDoc", "Point: ${p.label} - ${p.address} (${p.latitude}, ${p.longitude})")
                }
                
                if (safeDocumentId != null) {
                    repository.updateDocument(document, finalPoints)
                } else {
                    repository.saveDocument(document, finalPoints)
                }
                debugLogger.log("CreateDoc", "Save successful")
                onSuccess()
            } catch (e: Exception) {
                debugLogger.log("CreateDoc", "Error saving: ${e.message}")
                // Error handled
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentCreateScreen(
    onNavigateBack: () -> Unit,
    sharedFileUri: String? = null,
    documentId: String? = null,
    viewModel: DocumentCreateViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val existingDoc by viewModel.existingDocument.collectAsState()
    val existingPoints by viewModel.existingPoints.collectAsState()

    var name by remember { mutableStateOf<String>("") }
    var startDate by remember { mutableStateOf<Long>(System.currentTimeMillis()) }
    var endDate by remember { mutableStateOf<Long>(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L) }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var fileType by remember { mutableStateOf<String>("") }
    var radiusKm by remember { mutableStateOf<String>("10") }
    var globalMode by remember { mutableStateOf<Boolean>(false) }
    var countryMode by remember { mutableStateOf<Boolean>(false) }
    var countryCode by remember { mutableStateOf<String>("") }
    
    // State for the "Add New Area" form
    var locationQuery by remember { mutableStateOf<String>("") }
    
    // List of validity areas
    val validityAreas = remember { mutableStateListOf<DocumentPoint>() }
    
    val searchResults by viewModel.searchResults.collectAsState()

    LaunchedEffect(documentId, sharedFileUri) {
        val safeDocId = if (documentId.isNullOrBlank() || documentId == "{documentId}") null else documentId
        val safeSharedUri = if (sharedFileUri.isNullOrBlank() || sharedFileUri == "{sharedUri}") null else sharedFileUri
        
        if (safeDocId != null) {
            viewModel.loadDocument(safeDocId)
        }
        if (safeSharedUri != null) {
            val uri = Uri.parse(Uri.decode(safeSharedUri))
            selectedFileUri = uri
            fileType = context.contentResolver.getType(uri) ?: "application/octet-stream"
        }
    }

    LaunchedEffect(existingDoc) {
        existingDoc?.let { doc ->
            name = doc.name
            startDate = doc.startDate
            endDate = doc.endDate
            radiusKm = doc.defaultRadiusKm.toString()
            globalMode = doc.globalMode
            countryCode = doc.countryCode ?: ""
            countryMode = !doc.countryCode.isNullOrEmpty()
            
            if (selectedFileUri == null && doc.fileUri.isNotEmpty()) {
                selectedFileUri = Uri.parse(doc.fileUri)
                fileType = doc.fileType
            }
        }
    }

    LaunchedEffect(existingPoints) {
        if (existingPoints.isNotEmpty()) {
            validityAreas.clear()
            validityAreas.addAll(existingPoints)
        }
    }
    
    val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    
    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            fileType = context.contentResolver.getType(it) ?: "application/octet-stream"
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (documentId != null) "Editar Documento" else "Crear Documento") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del documento") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { filePicker.launch("*/*") }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Archivo del documento", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = selectedFileUri?.lastPathSegment ?: "Ningún archivo seleccionado",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(Icons.Default.AttachFile, contentDescription = null)
                }
            }
            
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val calendar = Calendar.getInstance().apply { timeInMillis = startDate }
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    calendar.set(year, month, day, hour, minute)
                                    startDate = calendar.timeInMillis
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            ).show()
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Fecha de inicio", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = dateFormat.format(Date(startDate)),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(Icons.Default.CalendarToday, contentDescription = null)
                }
            }
            
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val calendar = Calendar.getInstance().apply { timeInMillis = endDate }
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    calendar.set(year, month, day, hour, minute)
                                    endDate = calendar.timeInMillis
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            ).show()
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Fecha de fin", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = dateFormat.format(Date(endDate)),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(Icons.Default.CalendarToday, contentDescription = null)
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Modo global", style = MaterialTheme.typography.titleSmall)
                    Text(
                        "Válido en cualquier lugar",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = globalMode,
                    onCheckedChange = { globalMode = it }
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Validez por país", style = MaterialTheme.typography.titleSmall)
                    Text(
                        "Válido en todo el país (ej: DNI)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = countryMode,
                    onCheckedChange = { 
                        countryMode = it 
                        if (it) globalMode = false
                    },
                    enabled = !globalMode
                )
            }
            
            if (countryMode && !globalMode) {
                OutlinedTextField(
                    value = countryCode,
                    onValueChange = { countryCode = it.uppercase().take(2) },
                    label = { Text("Código de País (ISO, ej: ES)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Divider()

            if (!globalMode && !countryMode) {
                Text(
                    "Áreas de Validez (${validityAreas.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                // List of existing areas
                validityAreas.forEach { point ->
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(point.label ?: "Área sin nombre", style = MaterialTheme.typography.titleSmall)
                                if (!point.address.isNullOrBlank()) {
                                    Text(point.address, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                } else {
                                    Text("${point.latitude}, ${point.longitude} (${point.radiusKm} km)", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            IconButton(onClick = { validityAreas.remove(point) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    "Añadir nueva área",
                    style = MaterialTheme.typography.titleSmall,
                )
                
                OutlinedTextField(
                    value = radiusKm,
                    onValueChange = { radiusKm = it },
                    label = { Text("Radio de validación (km)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = locationQuery,
                    onValueChange = { 
                        locationQuery = it 
                        viewModel.searchLocation(it)
                    },
                    label = { Text("Buscar lugar (ej: Aeropuerto Barajas)") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (locationQuery.isNotEmpty()) {
                            IconButton(onClick = { locationQuery = ""; viewModel.searchLocation("") }) {
                                Icon(Icons.Default.Clear, contentDescription = null)
                            }
                        }
                    },
                    singleLine = true
                )

                if (searchResults.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column {
                            searchResults.forEach { result ->
                                ListItem(
                                    headlineContent = { Text(result.name) },
                                    supportingContent = { Text(result.fullAddress, maxLines = 1) },
                                    modifier = Modifier.clickable {
                                        val rad = radiusKm.toDoubleOrNull() ?: 10.0
                                        validityAreas.add(
                                            DocumentPoint(
                                                documentId = "", 
                                                latitude = result.latitude,
                                                longitude = result.longitude,
                                                radiusKm = rad,
                                                label = result.name,
                                                address = result.fullAddress
                                            )
                                        )
                                        locationQuery = ""
                                        viewModel.searchLocation("")
                                    }
                                )
                                if (result != searchResults.last()) Divider()
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val isEditing = documentId != null && documentId != "{documentId}"
            val hasName = name.isNotBlank()
            val hasFile = selectedFileUri != null || (isEditing && existingDoc?.fileUri?.isNotEmpty() == true)
            val hasLocation = globalMode || 
                           (countryMode && countryCode.isNotBlank()) || 
                           validityAreas.isNotEmpty()
            
            val canSave = hasName && hasFile && hasLocation

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!canSave) {
                    val missingFields = mutableListOf<String>()
                    if (!hasName) missingFields.add("nombre")
                    if (!hasFile) missingFields.add("archivo")
                    if (!hasLocation) missingFields.add("ubicación o modo global")
                    
                    Text(
                        text = "Falta: ${missingFields.joinToString(", ")}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Button(
                    onClick = {
                        val radius = radiusKm.toDoubleOrNull() ?: 10.0
                        
                        viewModel.saveDocument(
                            documentId = documentId,
                            name = name,
                            startDate = startDate,
                            endDate = endDate,
                            fileUri = selectedFileUri,
                            fileType = fileType,
                            radiusKm = radius,
                            globalMode = globalMode,
                            countryCode = if (countryMode) countryCode.ifBlank { null } else null,
                            validityAreas = validityAreas.toList(),
                            onSuccess = onNavigateBack
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canSave
                ) {
                    Text(if (isEditing) "Guardar cambios" else "Crear documento")
                }
            }
        }
    }
}
