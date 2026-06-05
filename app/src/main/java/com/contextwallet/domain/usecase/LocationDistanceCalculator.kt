package com.contextwallet.domain.usecase

import android.location.Location
import javax.inject.Inject
import kotlin.math.*

/**
 * Utility class for calculating distances between geographic coordinates
 * using the Haversine formula
 */
class LocationDistanceCalculator @Inject constructor() {
    
    companion object {
        private const val EARTH_RADIUS_KM = 6371.0
    }
    
    /**
     * Calculate distance between two points using Haversine formula
     * @return distance in kilometers
     */
    fun calculateDistance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)
        
        val c = 2 * asin(sqrt(a))
        
        return EARTH_RADIUS_KM * c
    }
    
    /**
     * Check if a location is within a specified radius of a point
     */
    fun isWithinRadius(
        userLat: Double,
        userLon: Double,
        pointLat: Double,
        pointLon: Double,
        radiusKm: Double
    ): Boolean {
        val distance = calculateDistance(userLat, userLon, pointLat, pointLon)
        return distance <= radiusKm
    }
    
    /**
     * Find the minimum distance to any point in a list
     * @return minimum distance in km, or null if list is empty
     */
    fun findMinimumDistance(
        userLat: Double,
        userLon: Double,
        points: List<Pair<Double, Double>>
    ): Double? {
        if (points.isEmpty()) return null
        
        return points.minOf { (lat, lon) ->
            calculateDistance(userLat, userLon, lat, lon)
        }
    }
    
    /**
     * Check if user is within radius of any validation point
     */
    fun isWithinAnyPoint(
        userLat: Double,
        userLon: Double,
        points: List<Triple<Double, Double, Double>> // lat, lon, radius
    ): Boolean {
        return points.any { (lat, lon, radius) ->
            isWithinRadius(userLat, userLon, lat, lon, radius)
        }
    }
}
