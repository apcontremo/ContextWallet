package com.contextwallet.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "document_points",
    foreignKeys = [
        ForeignKey(
            entity = Document::class,
            parentColumns = ["id"],
            childColumns = ["documentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("documentId")]
)
data class DocumentPoint(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val documentId: String,
    val latitude: Double,
    val longitude: Double,
    val radiusKm: Double, // Can override document's default radius
    val label: String? = null, // Optional name like "Airport Terminal 1"
    val address: String? = null // Human readable address
)
