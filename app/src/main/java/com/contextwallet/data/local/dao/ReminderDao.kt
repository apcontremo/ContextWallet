package com.contextwallet.data.local.dao

import androidx.room.*
import com.contextwallet.data.local.entity.ReminderType
import com.contextwallet.data.local.entity.ScheduledReminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ScheduledReminder): Long
    
    @Update
    suspend fun updateReminder(reminder: ScheduledReminder)
    
    @Delete
    suspend fun deleteReminder(reminder: ScheduledReminder)
    
    @Query("SELECT * FROM scheduled_reminders WHERE documentId = :documentId")
    suspend fun getRemindersForDocument(documentId: String): List<ScheduledReminder>
    
    @Query("SELECT * FROM scheduled_reminders WHERE documentId = :documentId")
    fun getRemindersForDocumentFlow(documentId: String): Flow<List<ScheduledReminder>>
    
    @Query("SELECT * FROM scheduled_reminders WHERE scheduledTime <= :currentTime AND sent = 0")
    suspend fun getPendingReminders(currentTime: Long): List<ScheduledReminder>
    
    @Query("SELECT * FROM scheduled_reminders WHERE documentId = :documentId AND reminderType = :type")
    suspend fun getReminderByType(documentId: String, type: ReminderType): ScheduledReminder?
    
    @Query("DELETE FROM scheduled_reminders WHERE documentId = :documentId")
    suspend fun deleteRemindersForDocument(documentId: String)
}
