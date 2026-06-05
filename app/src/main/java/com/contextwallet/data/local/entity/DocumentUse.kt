package com.contextwallet.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "document_uses",
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
data class DocumentUse(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val documentId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val latitude: Double?,
    val longitude: Double?
)
