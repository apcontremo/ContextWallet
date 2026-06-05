package com.contextwallet.data.repository

import com.contextwallet.data.local.dao.DocumentDao
import com.contextwallet.data.local.dao.ReminderDao
import com.contextwallet.data.local.entity.Document
import com.contextwallet.data.local.entity.DocumentPoint
import com.contextwallet.data.local.entity.DocumentUse
import com.contextwallet.domain.model.DocumentState
import com.contextwallet.domain.model.DocumentWithState
import com.contextwallet.domain.usecase.DocumentStateCalculator
import com.contextwallet.domain.usecase.ReminderScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepository @Inject constructor(
    private val documentDao: DocumentDao,
    private val reminderDao: ReminderDao,
    private val stateCalculator: DocumentStateCalculator,
    private val reminderScheduler: ReminderScheduler
) {
    
    /**
     * Get all documents with their calculated states
     */
    fun getAllDocumentsWithState(
        currentTime: Long,
        userLatitude: Double?,
        userLongitude: Double?,
        userCountryCode: String?
    ): Flow<List<DocumentWithState>> {
        return documentDao.getAllDocuments().map { documents ->
            documents.map { document ->
                val points = documentDao.getPointsForDocument(document.id)
                val state = stateCalculator.calculateState(
                    document, points, currentTime, userLatitude, userLongitude, userCountryCode
                )
                val distance = stateCalculator.calculateDistanceToNearestPoint(
                    points, userLatitude, userLongitude
                )
                val nearestPoint = stateCalculator.getNearestPoint(
                    points, userLatitude, userLongitude
                )
                val useCount = documentDao.getUseCount(document.id)
                
                DocumentWithState(document, points, state, distance, nearestPoint, useCount)
            }
        }
    }
    
    /**
     * Get documents filtered by state
     */
    fun getDocumentsByState(
        state: DocumentState,
        currentTime: Long,
        userLatitude: Double?,
        userLongitude: Double?,
        userCountryCode: String?
    ): Flow<List<DocumentWithState>> {
        return getAllDocumentsWithState(currentTime, userLatitude, userLongitude, userCountryCode)
            .map { documents ->
                documents.filter { it.state == state }
            }
    }
    
    /**
     * Get a single document with its state
     */
    suspend fun getDocumentWithState(
        documentId: String,
        currentTime: Long,
        userLatitude: Double?,
        userLongitude: Double?,
        userCountryCode: String?
    ): DocumentWithState? {
        val document = documentDao.getDocumentById(documentId) ?: return null
        val points = documentDao.getPointsForDocument(documentId)
        val state = stateCalculator.calculateState(
            document, points, currentTime, userLatitude, userLongitude, userCountryCode
        )
        val distance = stateCalculator.calculateDistanceToNearestPoint(
            points, userLatitude, userLongitude
        )
        val nearestPoint = stateCalculator.getNearestPoint(
            points, userLatitude, userLongitude
        )
        val useCount = documentDao.getUseCount(documentId)
        
        return DocumentWithState(document, points, state, distance, nearestPoint, useCount)
    }
    
    /**
     * Insert or update a document and schedule reminders
     */
    suspend fun saveDocument(document: Document, points: List<DocumentPoint>) {
        documentDao.saveDocumentWithPoints(document, points)
        
        // Schedule reminders
        reminderScheduler.scheduleReminders(document.id, document.startDate)
    }
    
    /**
     * Update an existing document
     */
    suspend fun updateDocument(document: Document, points: List<DocumentPoint>) {
        val oldDocument = documentDao.getDocumentById(document.id)
        
        documentDao.updateDocumentWithPoints(document, points)
        
        // Reschedule reminders if start date changed
        if (oldDocument != null && oldDocument.startDate != document.startDate) {
            reminderScheduler.rescheduleReminders(document.id, document.startDate)
        }
    }
    
    /**
     * Replace the file of an existing document
     */
    suspend fun replaceDocumentFile(documentId: String, newFilePath: String, newFileType: String) {
        val document = documentDao.getDocumentById(documentId) ?: return
        val updatedDocument = document.copy(
            fileUri = newFilePath,
            fileType = newFileType
        )
        documentDao.updateDocument(updatedDocument)
    }
    
    /**
     * Delete a document
     */
    suspend fun deleteDocument(document: Document) {
        reminderScheduler.cancelReminders(document.id)
        documentDao.deleteDocument(document)
    }
    
    /**
     * Add a validation point to a document
     */
    suspend fun addPoint(point: DocumentPoint) {
        documentDao.insertPoint(point)
    }
    
    /**
     * Remove a validation point
     */
    suspend fun removePoint(point: DocumentPoint) {
        documentDao.deletePoint(point)
    }
    
    /**
     * Record a document use
     */
    suspend fun recordUse(documentId: String, latitude: Double?, longitude: Double?) {
        val use = DocumentUse(
            documentId = documentId,
            latitude = latitude,
            longitude = longitude
        )
        documentDao.insertUse(use)
    }
    
    /**
     * Get usage history for a document
     */
    fun getUsageHistory(documentId: String): Flow<List<DocumentUse>> {
        return documentDao.getUsesForDocument(documentId)
    }
    
    /**
     * Get validation points for a document
     */
    fun getPoints(documentId: String): Flow<List<DocumentPoint>> {
        return documentDao.getPointsForDocumentFlow(documentId)
    }
}
