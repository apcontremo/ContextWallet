package com.contextwallet.domain.model

import com.contextwallet.data.local.entity.Document
import com.contextwallet.data.local.entity.DocumentPoint

/**
 * Combined data class containing a document and its validation points
 */
data class DocumentWithPoints(
    val document: Document,
    val points: List<DocumentPoint>
)

/**
 * Document with calculated state and additional metadata
 */
data class DocumentWithState(
    val document: Document,
    val points: List<DocumentPoint>,
    val state: DocumentState,
    val distanceToNearestPoint: Double? = null, // Distance in km
    val nearestPoint: DocumentPoint? = null,
    val useCount: Int = 0
)
