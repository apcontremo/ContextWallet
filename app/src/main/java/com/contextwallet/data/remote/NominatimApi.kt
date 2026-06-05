package com.contextwallet.data.remote

import com.contextwallet.data.remote.model.NominatimResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NominatimApi {
    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 10,
        @Query("addressdetails") addressDetails: Int = 1
    ): List<NominatimResult>
}
