package com.contextwallet.ui.navigation

sealed class Screen(val route: String) {
    object ActiveDocuments : Screen("active")
    object InactiveDocuments : Screen("inactive")
    object ExpiredDocuments : Screen("expired")
    object DocumentDetail : Screen("document/{documentId}") {
        fun createRoute(documentId: String) = "document/$documentId"
    }
    object DocumentEdit : Screen("edit/{documentId}") {
        fun createRoute(documentId: String) = "edit/$documentId"
    }
    object DocumentSelector : Screen("selector?sharedUri={sharedUri}") {
        fun createRoute(sharedUri: String) = "selector?sharedUri=$sharedUri"
    }
    object DocumentCreate : Screen("create?sharedUri={sharedUri}&documentId={documentId}") {
        fun createRoute(sharedUri: String? = null, documentId: String? = null): String {
            val uriPart = if (sharedUri != null) "sharedUri=$sharedUri" else null
            val idPart = if (documentId != null) "documentId=$documentId" else null
            val query = listOfNotNull(uriPart, idPart).joinToString("&")
            return if (query.isNotEmpty()) "create?$query" else "create"
        }
    }
    object Backup : Screen("backup")
}
