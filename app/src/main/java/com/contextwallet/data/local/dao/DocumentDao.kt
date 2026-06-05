package com.contextwallet.data.local.dao

import androidx.room.*
import com.contextwallet.data.local.entity.Document
import com.contextwallet.data.local.entity.DocumentPoint
import com.contextwallet.data.local.entity.DocumentUse
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: Document): Long
    
    @Update
    suspend fun updateDocument(document: Document)
    
    @Delete
    suspend fun deleteDocument(document: Document)
    
    @Query("SELECT * FROM documents WHERE id = :documentId")
    suspend fun getDocumentById(documentId: String): Document?
    
    @Query("SELECT * FROM documents WHERE id = :documentId")
    fun getDocumentByIdFlow(documentId: String): Flow<Document?>
    
    @Query("SELECT * FROM documents ORDER BY startDate ASC")
    fun getAllDocuments(): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE endDate < :currentTime ORDER BY endDate DESC")
    fun getExpiredDocuments(currentTime: Long): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE startDate > :currentTime ORDER BY startDate ASC")
    fun getNotStartedDocuments(currentTime: Long): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE startDate <= :currentTime AND endDate >= :currentTime ORDER BY name ASC")
    fun getInDateRangeDocuments(currentTime: Long): Flow<List<Document>>
    
    // Document Points
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point: DocumentPoint): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoints(points: List<DocumentPoint>)
    
    @Delete
    suspend fun deletePoint(point: DocumentPoint)
    
    @Query("SELECT * FROM document_points WHERE documentId = :documentId")
    suspend fun getPointsForDocument(documentId: String): List<DocumentPoint>
    
    @Query("SELECT * FROM document_points WHERE documentId = :documentId")
    fun getPointsForDocumentFlow(documentId: String): Flow<List<DocumentPoint>>
    
    @Query("DELETE FROM document_points WHERE documentId = :documentId")
    suspend fun deletePointsForDocument(documentId: String)
    
    // Document Uses
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUse(use: DocumentUse): Long
    
    @Query("SELECT * FROM document_uses WHERE documentId = :documentId ORDER BY timestamp DESC")
    fun getUsesForDocument(documentId: String): Flow<List<DocumentUse>>
    
    @Query("SELECT COUNT(*) FROM document_uses WHERE documentId = :documentId")
    suspend fun getUseCount(documentId: String): Int

    @Transaction
    suspend fun updateDocumentWithPoints(document: Document, points: List<DocumentPoint>) {
        updateDocument(document)
        deletePointsForDocument(document.id)
        if (points.isNotEmpty()) {
            insertPoints(points)
        }
    }

    @Transaction
    suspend fun saveDocumentWithPoints(document: Document, points: List<DocumentPoint>) {
        insertDocument(document)
        if (points.isNotEmpty()) {
            insertPoints(points)
        }
    }
}
