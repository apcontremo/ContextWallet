package com.contextwallet.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

enum class ReminderType {
    HOURS_24,
    HOURS_12
}

@Entity(
    tableName = "scheduled_reminders",
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
data class ScheduledReminder(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val documentId: String,
    val reminderType: ReminderType,
    val scheduledTime: Long, // Unix timestamp when reminder should fire
    val sent: Boolean = false,
    val workRequestId: String? = null // UUID of WorkManager task
)
