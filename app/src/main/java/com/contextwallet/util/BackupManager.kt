package com.contextwallet.util

import android.content.Context
import android.net.Uri
import com.contextwallet.data.local.AppDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: AppDatabase
) {

    private val documentsDir = File(context.filesDir, "documents")

    /**
     * Zips the database and documents into the provided URI
     */
    suspend fun exportData(destinationUri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openOutputStream(destinationUri)?.use { outputStream ->
                ZipOutputStream(BufferedOutputStream(outputStream)).use { zipOut ->
                    // 1. Export Database
                    val dbFile = context.getDatabasePath("contextwallet_database")
                    if (dbFile.exists()) {
                        addToZip(dbFile, "database/${dbFile.name}", zipOut)
                        
                        // Also include -wal and -shm files if they exist
                        File(dbFile.path + "-wal").let { if (it.exists()) addToZip(it, "database/${it.name}", zipOut) }
                        File(dbFile.path + "-shm").let { if (it.exists()) addToZip(it, "database/${it.name}", zipOut) }
                    }

                    // 2. Export Documents
                    if (documentsDir.exists() && documentsDir.isDirectory) {
                        documentsDir.listFiles()?.forEach { file ->
                            addToZip(file, "documents/${file.name}", zipOut)
                        }
                    }
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Unzips the backup and replaces current data
     */
    suspend fun importData(sourceUri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Close database before restoration
            database.close()

            context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                ZipInputStream(BufferedInputStream(inputStream)).use { zipIn ->
                    var entry: ZipEntry? = zipIn.nextEntry
                    while (entry != null) {
                        val file = when {
                            entry.name.startsWith("database/") -> {
                                val dbName = entry.name.substringAfter("database/")
                                context.getDatabasePath(dbName)
                            }
                            entry.name.startsWith("documents/") -> {
                                val docName = entry.name.substringAfter("documents/")
                                if (!documentsDir.exists()) documentsDir.mkdirs()
                                File(documentsDir, docName)
                            }
                            else -> null
                        }

                        file?.let {
                            it.parentFile?.mkdirs()
                            FileOutputStream(it).use { out ->
                                zipIn.copyTo(out)
                            }
                        }
                        zipIn.closeEntry()
                        entry = zipIn.nextEntry
                    }
                }
            }
            // Note: The app should probably be restarted or the database re-initialized here.
            // Since Room will re-open it on next access, we should be fine, but a process restart is cleaner.
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun addToZip(file: File, zipPath: String, zipOut: ZipOutputStream) {
        FileInputStream(file).use { fis ->
            val entry = ZipEntry(zipPath)
            zipOut.putNextEntry(entry)
            fis.copyTo(zipOut)
            zipOut.closeEntry()
        }
    }
}
