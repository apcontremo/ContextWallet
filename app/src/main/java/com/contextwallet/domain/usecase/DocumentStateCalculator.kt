package com.contextwallet.domain.usecase

import com.contextwallet.data.local.entity.Document
import com.contextwallet.data.local.entity.DocumentPoint
import com.contextwallet.domain.model.DocumentState
import javax.inject.Inject

/**
 * Core business logic for calculating document state based on time and location
 */
class DocumentStateCalculator @Inject constructor(
    private val locationCalculator: LocationDistanceCalculator
) {
    
    /**
     * Calculate the current state of a document
     * 
     * @param document The document to evaluate
     * @param points Validation points for the document
     * @param currentTime Current timestamp in milliseconds
     * @param userLatitude Current user latitude (null if location unavailable)
     * @param userLongitude Current user longitude (null if location unavailable)
     * @return The calculated DocumentState
     */
    fun calculateState(
        document: Document,
        points: List<DocumentPoint>,
        currentTime: Long,
        userLatitude: Double?,
        userLongitude: Double?,
        userCountryCode: String?
    ): DocumentState {
        // Rule 1: Check if expired
        if (currentTime > document.endDate) {
            return DocumentState.EXPIRED
        }
        
        // Rule 2: Check if not yet started
        if (currentTime < document.startDate) {
            return DocumentState.INACTIVE_NOT_STARTED
        }
        
        // At this point, we're within the valid date range
        // Rule 3: Check spatial validity
        
        // Global mode - always active when in date range
        if (document.globalMode) {
            return DocumentState.ACTIVE
        }
        
        // Country/Continent mode
        if (document.countryCode != null) {
            if (userCountryCode != null && userCountryCode.equals(document.countryCode, ignoreCase = true)) {
                return DocumentState.ACTIVE
            }
            // If country defined but not matched, continue to check validation points
            // (A document valid in ES could also have a specific point in FR)
        } else if (document.continentCode != null) {
             // Continent logic would go here
             return DocumentState.ACTIVE
        }
        
        // Location-based validation
        if (userLatitude == null || userLongitude == null) {
            // No location available - consider inactive
            return DocumentState.INACTIVE_OUT_OF_RANGE
        }
        
        // No validation points - treat as global
        if (points.isEmpty()) {
            return DocumentState.ACTIVE
        }
        
        // Check if within range of any validation point
        val isWithinRange = points.any { point ->
            locationCalculator.isWithinRadius(
                userLatitude,
                userLongitude,
                point.latitude,
                point.longitude,
                point.radiusKm
            )
        }
        
        return if (isWithinRange) {
            DocumentState.ACTIVE
        } else {
            DocumentState.INACTIVE_OUT_OF_RANGE
        }
    }
    
    /**
     * Calculate distance to nearest validation point
     * @return distance in km, or null if no points or no location
     */
    fun calculateDistanceToNearestPoint(
        points: List<DocumentPoint>,
        userLatitude: Double?,
        userLongitude: Double?
    ): Double? {
        if (userLatitude == null || userLongitude == null || points.isEmpty()) {
            return null
        }
        
        return points.minOf { point ->
            locationCalculator.calculateDistance(
                userLatitude,
                userLongitude,
                point.latitude,
                point.longitude
            )
        }
    }

    
    /**
     * Find the nearest validation point
     */
    fun getNearestPoint(
        points: List<DocumentPoint>,
        userLatitude: Double?,
        userLongitude: Double?
    ): DocumentPoint? {
        if (userLatitude == null || userLongitude == null || points.isEmpty()) {
            return null
        }
        
        return points.minByOrNull { point ->
            locationCalculator.calculateDistance(
                userLatitude,
                userLongitude,
                point.latitude,
                point.longitude
            )
        }
    }
}
