package com.contextwallet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.contextwallet.data.local.dao.DocumentDao
import com.contextwallet.data.local.dao.ReminderDao
import com.contextwallet.data.local.entity.Document
import com.contextwallet.data.local.entity.DocumentPoint
import com.contextwallet.data.local.entity.DocumentUse
import com.contextwallet.data.local.entity.ScheduledReminder

@Database(
    entities = [
        Document::class,
        DocumentPoint::class,
        DocumentUse::class,
        ScheduledReminder::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                // If migration failed previously, column might exist or not. 
                // Using try-catch or just adding column safe. 
                // Since this is dev environment, we'll increment version to 3 and do a clean migration strategy if needed.
                // For now, let's just make sure 1->2 adds the column.
                // NOTE: SQLite doesn't support IF NOT EXISTS for ADD COLUMN in standard syntax easily, 
                 // but since we control versioning, 1->2 happens once.
                try {
                    database.execSQL("ALTER TABLE document_points ADD COLUMN address TEXT")
                } catch (e: Exception) {
                    // Column might already exist
                }
            }
        }
        val MIGRATION_2_3 = object : androidx.room.migration.Migration(2, 3) {
             override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                 // Empty migration to force version bump and schema check
                 // Also ensure column exists just in case
                try {
                    database.execSQL("ALTER TABLE document_points ADD COLUMN address TEXT")
                } catch (e: Exception) {
                    // Column already exists, ignore
                }
             }
        }
    }
}
