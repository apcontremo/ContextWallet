package com.contextwallet.data.repository

import com.contextwallet.data.remote.NominatimApi
import com.contextwallet.ui.screens.SearchResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class GeocodingRepository @Inject constructor(
    private val api: NominatimApi,
    private val logger: com.contextwallet.util.DebugLogger
) {
    suspend fun searchLocation(query: String): List<SearchResult> {
        logger.log("GeoRepo", "Searching for: $query")
        return try {
            val results = api.search(query = query)
            val finalResults = results.map { result ->
                val name = result.address?.amenity 
                    ?: result.address?.shop 
                    ?: result.address?.leisure 
                    ?: result.address?.tourism 
                    ?: result.address?.building 
                    ?: result.displayName.substringBefore(",")
                
                val addressParts = listOfNotNull(
                    result.address?.road,
                    result.address?.city ?: result.address?.town ?: result.address?.village,
                    result.address?.country
                )
                
                SearchResult(
                    name = name.capitalize(),
                    latitude = result.lat.toDoubleOrNull() ?: 0.0,
                    longitude = result.lon.toDoubleOrNull() ?: 0.0,
                    fullAddress = result.displayName
                )
            }
            logger.log("GeoRepo", "Found ${finalResults.size} results")
            finalResults
        } catch (e: Exception) {
            logger.log("GeoRepo", "Error: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
    
    private fun String.capitalize(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(java.util.Locale.getDefault()) else it.toString() }
    }
}
