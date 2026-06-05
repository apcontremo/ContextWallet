package com.contextwallet.domain.model

enum class DocumentState {
    ACTIVE,
    INACTIVE_NOT_STARTED,
    INACTIVE_OUT_OF_RANGE,
    EXPIRED
}

fun DocumentState.getDisplayText(): String {
    return when (this) {
        DocumentState.ACTIVE -> "Activo"
        DocumentState.INACTIVE_NOT_STARTED -> "Aún no válido"
        DocumentState.INACTIVE_OUT_OF_RANGE -> "Fuera de rango"
        DocumentState.EXPIRED -> "Caducado"
    }
}

fun DocumentState.getDescription(startDate: Long? = null, distanceKm: Double? = null): String {
    return when (this) {
        DocumentState.ACTIVE -> "Este documento está actualmente válido"
        DocumentState.INACTIVE_NOT_STARTED -> {
            if (startDate != null) {
                "Comienza el ${java.text.SimpleDateFormat("dd MMM yyyy HH:mm", java.util.Locale("es", "ES")).format(java.util.Date(startDate))}"
            } else {
                "Aún no ha comenzado"
            }
        }
        DocumentState.INACTIVE_OUT_OF_RANGE -> {
            if (distanceKm != null) {
                "%.1f km del punto más cercano".format(distanceKm)
            } else {
                "Fuera del rango de validación"
            }
        }
        DocumentState.EXPIRED -> "Este documento ha caducado"
    }
}
