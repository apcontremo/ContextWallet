package com.contextwallet.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.contextwallet.R
import com.contextwallet.data.local.dao.DocumentDao
import com.contextwallet.data.local.dao.ReminderDao
import com.contextwallet.data.local.entity.ReminderType
import com.contextwallet.domain.usecase.ReminderScheduler
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val documentDao: DocumentDao,
    private val reminderDao: ReminderDao
) : CoroutineWorker(context, workerParams) {
    
    companion object {
        private const val CHANNEL_ID = "document_reminders"
        private const val CHANNEL_NAME = "Document Reminders"
        private const val NOTIFICATION_ID_BASE = 1000
    }
    
    override suspend fun doWork(): Result {
        val documentId = inputData.getString(ReminderScheduler.KEY_DOCUMENT_ID)
            ?: return Result.failure()
        val reminderTypeStr = inputData.getString(ReminderScheduler.KEY_REMINDER_TYPE)
            ?: return Result.failure()
        
        val reminderType = try {
            ReminderType.valueOf(reminderTypeStr)
        } catch (e: IllegalArgumentException) {
            return Result.failure()
        }
        
        // Get document
        val document = documentDao.getDocumentById(documentId)
            ?: return Result.failure()
        
        // Create notification
        createNotificationChannel()
        showNotification(document.name, reminderType)
        
        // Mark reminder as sent
        val reminder = reminderDao.getReminderByType(documentId, reminderType)
        if (reminder != null) {
            reminderDao.updateReminder(reminder.copy(sent = true))
        }
        
        return Result.success()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for upcoming document activations"
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun showNotification(documentName: String, reminderType: ReminderType) {
        val message = when (reminderType) {
            ReminderType.HOURS_24 -> "\"$documentName\" will be active in 24 hours"
            ReminderType.HOURS_12 -> "\"$documentName\" will be active in 12 hours"
        }
        
        // Create intent to open app (will be implemented with MainActivity)
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // TODO: Replace with app icon
            .setContentTitle("Document Reminder")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_BASE + reminderType.ordinal, notification)
    }
}
