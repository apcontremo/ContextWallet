package com.contextwallet.domain.usecase

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.contextwallet.data.local.dao.ReminderDao
import com.contextwallet.data.local.entity.ReminderType
import com.contextwallet.data.local.entity.ScheduledReminder
import com.contextwallet.workers.ReminderWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Manages scheduling of document activation reminders
 */
class ReminderScheduler @Inject constructor(
    private val workManager: WorkManager,
    private val reminderDao: ReminderDao
) {
    
    companion object {
        private const val HOURS_24_MS = 24 * 60 * 60 * 1000L
        private const val HOURS_12_MS = 12 * 60 * 60 * 1000L
        const val KEY_DOCUMENT_ID = "document_id"
        const val KEY_REMINDER_TYPE = "reminder_type"
    }
    
    /**
     * Schedule reminders for a document
     * Only schedules reminders that haven't passed yet
     */
    suspend fun scheduleReminders(documentId: String, startDate: Long) {
        val currentTime = System.currentTimeMillis()
        
        // Calculate reminder times
        val reminder24h = startDate - HOURS_24_MS
        val reminder12h = startDate - HOURS_12_MS
        
        // Schedule 24-hour reminder if it's in the future
        if (reminder24h > currentTime) {
            scheduleReminder(documentId, ReminderType.HOURS_24, reminder24h)
        }
        
        // Schedule 12-hour reminder if it's in the future
        if (reminder12h > currentTime) {
            scheduleReminder(documentId, ReminderType.HOURS_12, reminder12h)
        }
    }
    
    /**
     * Schedule a single reminder
     */
    private suspend fun scheduleReminder(
        documentId: String,
        reminderType: ReminderType,
        scheduledTime: Long
    ) {
        // Check if reminder already exists
        val existing = reminderDao.getReminderByType(documentId, reminderType)
        if (existing != null) {
            return // Already scheduled
        }
        
        val currentTime = System.currentTimeMillis()
        val delay = scheduledTime - currentTime
        
        if (delay <= 0) {
            return // Time has passed
        }
        
        // Create WorkManager request
        val inputData = Data.Builder()
            .putString(KEY_DOCUMENT_ID, documentId)
            .putString(KEY_REMINDER_TYPE, reminderType.name)
            .build()
        
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag("reminder_$documentId")
            .build()
        
        // Enqueue work
        workManager.enqueue(workRequest)
        
        // Save to database
        val reminder = ScheduledReminder(
            documentId = documentId,
            reminderType = reminderType,
            scheduledTime = scheduledTime,
            workRequestId = workRequest.id.toString()
        )
        reminderDao.insertReminder(reminder)
    }
    
    /**
     * Cancel all reminders for a document
     */
    suspend fun cancelReminders(documentId: String) {
        // Cancel WorkManager tasks
        workManager.cancelAllWorkByTag("reminder_$documentId")
        
        // Delete from database
        reminderDao.deleteRemindersForDocument(documentId)
    }
    
    /**
     * Reschedule reminders for a document (useful when dates change)
     */
    suspend fun rescheduleReminders(documentId: String, newStartDate: Long) {
        cancelReminders(documentId)
        scheduleReminders(documentId, newStartDate)
    }
}
