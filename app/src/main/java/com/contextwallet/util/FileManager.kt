package com.contextwallet.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    private val documentsDir: File
        get() = File(context.filesDir, "documents").also { it.mkdirs() }
    
    /**
     * Copy a file from external URI to app-private storage
     * @return Internal file path
     */
    fun copyToInternalStorage(sourceUri: Uri, mimeType: String): String {
        val extension = getExtensionFromMimeType(mimeType)
        val fileName = "${UUID.randomUUID()}.$extension"
        val destFile = File(documentsDir, fileName)
        
        context.contentResolver.openInputStream(sourceUri)?.use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        }
        
        return destFile.absolutePath
    }
    
    /**
     * Get file extension from MIME type
     */
    private fun getExtensionFromMimeType(mimeType: String): String {
        return when {
            mimeType.startsWith("image/") -> {
                when {
                    mimeType.contains("jpeg") || mimeType.contains("jpg") -> "jpg"
                    mimeType.contains("png") -> "png"
                    mimeType.contains("gif") -> "gif"
                    mimeType.contains("webp") -> "webp"
                    else -> "img"
                }
            }
            mimeType == "application/pdf" -> "pdf"
            mimeType.startsWith("text/") -> "txt"
            else -> "dat"
        }
    }
    
    /**
     * Delete a file from internal storage
     */
    fun deleteFile(filePath: String): Boolean {
        val file = File(filePath)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }
    
    /**
     * Get file size in bytes
     */
    fun getFileSize(filePath: String): Long {
        val file = File(filePath)
        return if (file.exists()) file.length() else 0
    }
    
    /**
     * Check if file exists
     */
    fun fileExists(filePath: String): Boolean {
        return File(filePath).exists()
    }
    
    /**
     * Replace an existing file with a new one
     * Deletes the old file and copies the new file to internal storage
     * @return New internal file path
     */
    fun replaceFile(oldFilePath: String, sourceUri: Uri, mimeType: String): String {
        // Delete old file if it exists
        deleteFile(oldFilePath)
        
        // Copy new file
        return copyToInternalStorage(sourceUri, mimeType)
    }
    
    /**
     * Get URI for sharing a file
     */
    fun getShareableUri(filePath: String): Uri? {
        val file = File(filePath)
        return if (file.exists()) {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } else {
            null
        }
    }
}
