package com.contextwallet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "documents")
data class Document(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val startDate: Long, // Unix timestamp in milliseconds
    val endDate: Long, // Unix timestamp in milliseconds
    val fileUri: String, // Local file path
    val fileType: String, // MIME type (image/jpeg, application/pdf, etc.)
    val defaultRadiusKm: Double = 10.0,
    val globalMode: Boolean = false, // Valid worldwide
    val countryCode: String? = null, // ISO country code for country mode
    val continentCode: String? = null, // Continent code for continent mode
    val createdAt: Long = System.currentTimeMillis(),
    val lastModified: Long = System.currentTimeMillis()
)
