package com.contextwallet.data.remote.model

import com.google.gson.annotations.SerializedName

data class NominatimResult(
    @SerializedName("place_id") val placeId: Long,
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("address") val address: NominatimAddress?
)

data class NominatimAddress(
    @SerializedName("road") val road: String?,
    @SerializedName("house_number") val houseNumber: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("town") val town: String?,
    @SerializedName("village") val village: String?,
    @SerializedName("postcode") val postcode: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("amenity") val amenity: String?, // e.g. fuel, hospital
    @SerializedName("shop") val shop: String?, // e.g. supermarket
    @SerializedName("leisure") val leisure: String?,
    @SerializedName("tourism") val tourism: String?,
    @SerializedName("building") val building: String?
)
