package com.contextwallet.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.contextwallet.ui.navigation.Screen
import com.contextwallet.ui.screens.*
import com.contextwallet.ui.theme.ContextWalletTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.res.stringResource
import com.contextwallet.R
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle permission result
    }
    
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        // Handle permission result
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        requestPermissions()
        
        // Handle shared document from other apps
        val sharedFileUri = handleSharedIntent(intent)
        
        setContent {
            ContextWalletTheme {
                MainScreen(sharedFileUri = sharedFileUri)
            }
        }
    }
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        // Handle new shared document
        val sharedFileUri = handleSharedIntent(intent)
        if (sharedFileUri != null) {
            // Recreate to pass new URI
            recreate()
        }
    }
    
    private fun handleSharedIntent(intent: Intent): Uri? {
        return when {
            intent.action == Intent.ACTION_SEND -> {
                intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            }
            else -> null
        }
    }
    
    private fun requestPermissions() {
        // Request location permissions
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        
        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(sharedFileUri: Uri? = null) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Navegar a la pantalla de selección si hay un archivo compartido
    LaunchedEffect(sharedFileUri) {
        if (sharedFileUri != null) {
            navController.navigate("selector?sharedUri=${Uri.encode(sharedFileUri.toString())}")
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { 
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "ContextWallet",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.CenterStart
                    ) 
                },
                actions = {
                    // Backup button
                    IconButton(onClick = { navController.navigate(Screen.Backup.route) }) {
                        Icon(Icons.Default.Backup, contentDescription = "Copia de Seguridad")
                    }
                    // Debug button
                    IconButton(onClick = { navController.navigate("log_viewer") }) {
                        Icon(Icons.Default.BugReport, contentDescription = "Debug Logs")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Opaque header
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            if (currentRoute in listOf(
                Screen.ActiveDocuments.route,
                Screen.InactiveDocuments.route,
                Screen.ExpiredDocuments.route
            )) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                        label = { Text(stringResource(R.string.nav_active)) },
                        selected = currentRoute == Screen.ActiveDocuments.route,
                        onClick = {
                            navController.navigate(Screen.ActiveDocuments.route) {
                                popUpTo(Screen.ActiveDocuments.route) { inclusive = true }
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                        label = { Text(stringResource(R.string.nav_inactive)) },
                        selected = currentRoute == Screen.InactiveDocuments.route,
                        onClick = {
                            navController.navigate(Screen.InactiveDocuments.route) {
                                popUpTo(Screen.ActiveDocuments.route)
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.History, contentDescription = null) },
                        label = { Text(stringResource(R.string.nav_expired)) },
                        selected = currentRoute == Screen.ExpiredDocuments.route,
                        onClick = {
                            navController.navigate(Screen.ExpiredDocuments.route) {
                                popUpTo(Screen.ActiveDocuments.route)
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen de fondo con brillo ajustado
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.cw_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.9f) // Ajustar el brillo
            )
            
                NavHost(
                    navController = navController,
                    startDestination = Screen.ActiveDocuments.route,
                    modifier = Modifier.padding(padding)
                ) {
                    composable(Screen.ActiveDocuments.route) {
                        ActiveDocumentsScreen(
                            onDocumentClick = { id ->
                                navController.navigate(Screen.DocumentDetail.createRoute(id))
                            },
                            onCreateClick = {
                                navController.navigate(Screen.DocumentCreate.createRoute())
                            }
                        )
                    }

                    composable(Screen.InactiveDocuments.route) {
                        InactiveDocumentsScreen(
                            onDocumentClick = { id ->
                                navController.navigate(Screen.DocumentDetail.createRoute(id))
                            }
                        )
                    }

                    composable(Screen.ExpiredDocuments.route) {
                        ExpiredDocumentsScreen(
                            onDocumentClick = { id ->
                                navController.navigate(Screen.DocumentDetail.createRoute(id))
                            }
                        )
                    }

                    composable(
                        route = Screen.DocumentDetail.route,
                        arguments = listOf(navArgument("documentId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val documentId = backStackEntry.arguments?.getString("documentId") ?: return@composable
                        DocumentDetailScreen(
                            documentId = documentId,
                            onNavigateBack = { navController.popBackStack() },
                            onEditDocument = { id ->
                                navController.navigate(Screen.DocumentCreate.createRoute(documentId = id))
                            }
                        )
                    }

                    composable(
                        route = "selector?sharedUri={sharedUri}",
                        arguments = listOf(
                            navArgument("sharedUri") {
                                type = NavType.StringType
                                nullable = true
                                defaultValue = null
                            }
                        )
                    ) { backStackEntry ->
                        val sharedUri = backStackEntry.arguments?.getString("sharedUri")
                        if (sharedUri != null) {
                            DocumentSelectorScreen(
                                sharedFileUri = sharedUri,
                                onCreateNew = {
                                    navController.navigate(Screen.DocumentCreate.createRoute(sharedUri = sharedUri)) {
                                        popUpTo(Screen.DocumentSelector.createRoute(sharedUri)) { inclusive = true }
                                    }
                                },
                                onReplace = { docId ->
                                    navController.navigate(Screen.DocumentCreate.createRoute(sharedUri = sharedUri, documentId = docId)) {
                                        popUpTo(Screen.DocumentSelector.createRoute(sharedUri)) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }

                    composable(
                        route = Screen.DocumentCreate.route,
                        arguments = listOf(
                            navArgument("sharedUri") { 
                                type = NavType.StringType
                                nullable = true
                                defaultValue = null
                            },
                            navArgument("documentId") {
                                type = NavType.StringType
                                nullable = true
                                defaultValue = null
                            }
                        )
                    ) { backStackEntry ->
                        DocumentCreateScreen(
                            onNavigateBack = { navController.popBackStack() },
                            sharedFileUri = backStackEntry.arguments?.getString("sharedUri"),
                            documentId = backStackEntry.arguments?.getString("documentId")
                        )
                    }
                    composable("log_viewer") {
                        val logger = androidx.hilt.navigation.compose.hiltViewModel<com.contextwallet.ui.screens.LogViewerViewModel>()
                        com.contextwallet.ui.screens.LogViewerScreen(
                            onNavigateBack = { navController.popBackStack() },
                            logger = logger.logger
                        )
                    }
                    composable(Screen.Backup.route) {
                        BackupScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
        }
    }
}


