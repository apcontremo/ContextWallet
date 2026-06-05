package com.contextwallet.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.contextwallet.data.local.entity.Document;
import com.contextwallet.data.local.entity.DocumentPoint;
import com.contextwallet.data.local.entity.DocumentUse;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DocumentDao_Impl implements DocumentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Document> __insertionAdapterOfDocument;

  private final EntityInsertionAdapter<DocumentPoint> __insertionAdapterOfDocumentPoint;

  private final EntityInsertionAdapter<DocumentUse> __insertionAdapterOfDocumentUse;

  private final EntityDeletionOrUpdateAdapter<Document> __deletionAdapterOfDocument;

  private final EntityDeletionOrUpdateAdapter<DocumentPoint> __deletionAdapterOfDocumentPoint;

  private final EntityDeletionOrUpdateAdapter<Document> __updateAdapterOfDocument;

  private final SharedSQLiteStatement __preparedStmtOfDeletePointsForDocument;

  public DocumentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDocument = new EntityInsertionAdapter<Document>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `documents` (`id`,`name`,`startDate`,`endDate`,`fileUri`,`fileType`,`defaultRadiusKm`,`globalMode`,`countryCode`,`continentCode`,`createdAt`,`lastModified`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Document entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getStartDate());
        statement.bindLong(4, entity.getEndDate());
        statement.bindString(5, entity.getFileUri());
        statement.bindString(6, entity.getFileType());
        statement.bindDouble(7, entity.getDefaultRadiusKm());
        final int _tmp = entity.getGlobalMode() ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.getCountryCode() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getCountryCode());
        }
        if (entity.getContinentCode() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getContinentCode());
        }
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getLastModified());
      }
    };
    this.__insertionAdapterOfDocumentPoint = new EntityInsertionAdapter<DocumentPoint>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `document_points` (`id`,`documentId`,`latitude`,`longitude`,`radiusKm`,`label`,`address`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DocumentPoint entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDocumentId());
        statement.bindDouble(3, entity.getLatitude());
        statement.bindDouble(4, entity.getLongitude());
        statement.bindDouble(5, entity.getRadiusKm());
        if (entity.getLabel() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getLabel());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAddress());
        }
      }
    };
    this.__insertionAdapterOfDocumentUse = new EntityInsertionAdapter<DocumentUse>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `document_uses` (`id`,`documentId`,`timestamp`,`latitude`,`longitude`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DocumentUse entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDocumentId());
        statement.bindLong(3, entity.getTimestamp());
        if (entity.getLatitude() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLongitude());
        }
      }
    };
    this.__deletionAdapterOfDocument = new EntityDeletionOrUpdateAdapter<Document>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `documents` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Document entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__deletionAdapterOfDocumentPoint = new EntityDeletionOrUpdateAdapter<DocumentPoint>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `document_points` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DocumentPoint entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfDocument = new EntityDeletionOrUpdateAdapter<Document>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `documents` SET `id` = ?,`name` = ?,`startDate` = ?,`endDate` = ?,`fileUri` = ?,`fileType` = ?,`defaultRadiusKm` = ?,`globalMode` = ?,`countryCode` = ?,`continentCode` = ?,`createdAt` = ?,`lastModified` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Document entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getStartDate());
        statement.bindLong(4, entity.getEndDate());
        statement.bindString(5, entity.getFileUri());
        statement.bindString(6, entity.getFileType());
        statement.bindDouble(7, entity.getDefaultRadiusKm());
        final int _tmp = entity.getGlobalMode() ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.getCountryCode() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getCountryCode());
        }
        if (entity.getContinentCode() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getContinentCode());
        }
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getLastModified());
        statement.bindString(13, entity.getId());
      }
    };
    this.__preparedStmtOfDeletePointsForDocument = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM document_points WHERE documentId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertDocument(final Document document,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDocument.insertAndReturnId(document);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPoint(final DocumentPoint point,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDocumentPoint.insertAndReturnId(point);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPoints(final List<DocumentPoint> points,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDocumentPoint.insert(points);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertUse(final DocumentUse use, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDocumentUse.insertAndReturnId(use);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDocument(final Document document,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfDocument.handle(document);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePoint(final DocumentPoint point,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfDocumentPoint.handle(point);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDocument(final Document document,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfDocument.handle(document);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDocumentWithPoints(final Document document, final List<DocumentPoint> points,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> DocumentDao.DefaultImpls.updateDocumentWithPoints(DocumentDao_Impl.this, document, points, __cont), $completion);
  }

  @Override
  public Object saveDocumentWithPoints(final Document document, final List<DocumentPoint> points,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> DocumentDao.DefaultImpls.saveDocumentWithPoints(DocumentDao_Impl.this, document, points, __cont), $completion);
  }

  @Override
  public Object deletePointsForDocument(final String documentId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePointsForDocument.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, documentId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePointsForDocument.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getDocumentById(final String documentId,
      final Continuation<? super Document> $completion) {
    final String _sql = "SELECT * FROM documents WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Document>() {
      @Override
      @Nullable
      public Document call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFileUri = CursorUtil.getColumnIndexOrThrow(_cursor, "fileUri");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfDefaultRadiusKm = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRadiusKm");
          final int _cursorIndexOfGlobalMode = CursorUtil.getColumnIndexOrThrow(_cursor, "globalMode");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfContinentCode = CursorUtil.getColumnIndexOrThrow(_cursor, "continentCode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final Document _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpFileUri;
            _tmpFileUri = _cursor.getString(_cursorIndexOfFileUri);
            final String _tmpFileType;
            _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            final double _tmpDefaultRadiusKm;
            _tmpDefaultRadiusKm = _cursor.getDouble(_cursorIndexOfDefaultRadiusKm);
            final boolean _tmpGlobalMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfGlobalMode);
            _tmpGlobalMode = _tmp != 0;
            final String _tmpCountryCode;
            if (_cursor.isNull(_cursorIndexOfCountryCode)) {
              _tmpCountryCode = null;
            } else {
              _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            }
            final String _tmpContinentCode;
            if (_cursor.isNull(_cursorIndexOfContinentCode)) {
              _tmpContinentCode = null;
            } else {
              _tmpContinentCode = _cursor.getString(_cursorIndexOfContinentCode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new Document(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpFileUri,_tmpFileType,_tmpDefaultRadiusKm,_tmpGlobalMode,_tmpCountryCode,_tmpContinentCode,_tmpCreatedAt,_tmpLastModified);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Document> getDocumentByIdFlow(final String documentId) {
    final String _sql = "SELECT * FROM documents WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"documents"}, new Callable<Document>() {
      @Override
      @Nullable
      public Document call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFileUri = CursorUtil.getColumnIndexOrThrow(_cursor, "fileUri");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfDefaultRadiusKm = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRadiusKm");
          final int _cursorIndexOfGlobalMode = CursorUtil.getColumnIndexOrThrow(_cursor, "globalMode");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfContinentCode = CursorUtil.getColumnIndexOrThrow(_cursor, "continentCode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final Document _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpFileUri;
            _tmpFileUri = _cursor.getString(_cursorIndexOfFileUri);
            final String _tmpFileType;
            _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            final double _tmpDefaultRadiusKm;
            _tmpDefaultRadiusKm = _cursor.getDouble(_cursorIndexOfDefaultRadiusKm);
            final boolean _tmpGlobalMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfGlobalMode);
            _tmpGlobalMode = _tmp != 0;
            final String _tmpCountryCode;
            if (_cursor.isNull(_cursorIndexOfCountryCode)) {
              _tmpCountryCode = null;
            } else {
              _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            }
            final String _tmpContinentCode;
            if (_cursor.isNull(_cursorIndexOfContinentCode)) {
              _tmpContinentCode = null;
            } else {
              _tmpContinentCode = _cursor.getString(_cursorIndexOfContinentCode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new Document(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpFileUri,_tmpFileType,_tmpDefaultRadiusKm,_tmpGlobalMode,_tmpCountryCode,_tmpContinentCode,_tmpCreatedAt,_tmpLastModified);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Document>> getAllDocuments() {
    final String _sql = "SELECT * FROM documents ORDER BY startDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"documents"}, new Callable<List<Document>>() {
      @Override
      @NonNull
      public List<Document> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFileUri = CursorUtil.getColumnIndexOrThrow(_cursor, "fileUri");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfDefaultRadiusKm = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRadiusKm");
          final int _cursorIndexOfGlobalMode = CursorUtil.getColumnIndexOrThrow(_cursor, "globalMode");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfContinentCode = CursorUtil.getColumnIndexOrThrow(_cursor, "continentCode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Document> _result = new ArrayList<Document>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Document _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpFileUri;
            _tmpFileUri = _cursor.getString(_cursorIndexOfFileUri);
            final String _tmpFileType;
            _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            final double _tmpDefaultRadiusKm;
            _tmpDefaultRadiusKm = _cursor.getDouble(_cursorIndexOfDefaultRadiusKm);
            final boolean _tmpGlobalMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfGlobalMode);
            _tmpGlobalMode = _tmp != 0;
            final String _tmpCountryCode;
            if (_cursor.isNull(_cursorIndexOfCountryCode)) {
              _tmpCountryCode = null;
            } else {
              _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            }
            final String _tmpContinentCode;
            if (_cursor.isNull(_cursorIndexOfContinentCode)) {
              _tmpContinentCode = null;
            } else {
              _tmpContinentCode = _cursor.getString(_cursorIndexOfContinentCode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Document(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpFileUri,_tmpFileType,_tmpDefaultRadiusKm,_tmpGlobalMode,_tmpCountryCode,_tmpContinentCode,_tmpCreatedAt,_tmpLastModified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Document>> getExpiredDocuments(final long currentTime) {
    final String _sql = "SELECT * FROM documents WHERE endDate < ? ORDER BY endDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"documents"}, new Callable<List<Document>>() {
      @Override
      @NonNull
      public List<Document> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFileUri = CursorUtil.getColumnIndexOrThrow(_cursor, "fileUri");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfDefaultRadiusKm = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRadiusKm");
          final int _cursorIndexOfGlobalMode = CursorUtil.getColumnIndexOrThrow(_cursor, "globalMode");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfContinentCode = CursorUtil.getColumnIndexOrThrow(_cursor, "continentCode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Document> _result = new ArrayList<Document>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Document _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpFileUri;
            _tmpFileUri = _cursor.getString(_cursorIndexOfFileUri);
            final String _tmpFileType;
            _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            final double _tmpDefaultRadiusKm;
            _tmpDefaultRadiusKm = _cursor.getDouble(_cursorIndexOfDefaultRadiusKm);
            final boolean _tmpGlobalMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfGlobalMode);
            _tmpGlobalMode = _tmp != 0;
            final String _tmpCountryCode;
            if (_cursor.isNull(_cursorIndexOfCountryCode)) {
              _tmpCountryCode = null;
            } else {
              _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            }
            final String _tmpContinentCode;
            if (_cursor.isNull(_cursorIndexOfContinentCode)) {
              _tmpContinentCode = null;
            } else {
              _tmpContinentCode = _cursor.getString(_cursorIndexOfContinentCode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Document(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpFileUri,_tmpFileType,_tmpDefaultRadiusKm,_tmpGlobalMode,_tmpCountryCode,_tmpContinentCode,_tmpCreatedAt,_tmpLastModified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Document>> getNotStartedDocuments(final long currentTime) {
    final String _sql = "SELECT * FROM documents WHERE startDate > ? ORDER BY startDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"documents"}, new Callable<List<Document>>() {
      @Override
      @NonNull
      public List<Document> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFileUri = CursorUtil.getColumnIndexOrThrow(_cursor, "fileUri");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfDefaultRadiusKm = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRadiusKm");
          final int _cursorIndexOfGlobalMode = CursorUtil.getColumnIndexOrThrow(_cursor, "globalMode");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfContinentCode = CursorUtil.getColumnIndexOrThrow(_cursor, "continentCode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Document> _result = new ArrayList<Document>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Document _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpFileUri;
            _tmpFileUri = _cursor.getString(_cursorIndexOfFileUri);
            final String _tmpFileType;
            _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            final double _tmpDefaultRadiusKm;
            _tmpDefaultRadiusKm = _cursor.getDouble(_cursorIndexOfDefaultRadiusKm);
            final boolean _tmpGlobalMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfGlobalMode);
            _tmpGlobalMode = _tmp != 0;
            final String _tmpCountryCode;
            if (_cursor.isNull(_cursorIndexOfCountryCode)) {
              _tmpCountryCode = null;
            } else {
              _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            }
            final String _tmpContinentCode;
            if (_cursor.isNull(_cursorIndexOfContinentCode)) {
              _tmpContinentCode = null;
            } else {
              _tmpContinentCode = _cursor.getString(_cursorIndexOfContinentCode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Document(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpFileUri,_tmpFileType,_tmpDefaultRadiusKm,_tmpGlobalMode,_tmpCountryCode,_tmpContinentCode,_tmpCreatedAt,_tmpLastModified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Document>> getInDateRangeDocuments(final long currentTime) {
    final String _sql = "SELECT * FROM documents WHERE startDate <= ? AND endDate >= ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, currentTime);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"documents"}, new Callable<List<Document>>() {
      @Override
      @NonNull
      public List<Document> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFileUri = CursorUtil.getColumnIndexOrThrow(_cursor, "fileUri");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfDefaultRadiusKm = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRadiusKm");
          final int _cursorIndexOfGlobalMode = CursorUtil.getColumnIndexOrThrow(_cursor, "globalMode");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfContinentCode = CursorUtil.getColumnIndexOrThrow(_cursor, "continentCode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Document> _result = new ArrayList<Document>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Document _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpFileUri;
            _tmpFileUri = _cursor.getString(_cursorIndexOfFileUri);
            final String _tmpFileType;
            _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            final double _tmpDefaultRadiusKm;
            _tmpDefaultRadiusKm = _cursor.getDouble(_cursorIndexOfDefaultRadiusKm);
            final boolean _tmpGlobalMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfGlobalMode);
            _tmpGlobalMode = _tmp != 0;
            final String _tmpCountryCode;
            if (_cursor.isNull(_cursorIndexOfCountryCode)) {
              _tmpCountryCode = null;
            } else {
              _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            }
            final String _tmpContinentCode;
            if (_cursor.isNull(_cursorIndexOfContinentCode)) {
              _tmpContinentCode = null;
            } else {
              _tmpContinentCode = _cursor.getString(_cursorIndexOfContinentCode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Document(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpFileUri,_tmpFileType,_tmpDefaultRadiusKm,_tmpGlobalMode,_tmpCountryCode,_tmpContinentCode,_tmpCreatedAt,_tmpLastModified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPointsForDocument(final String documentId,
      final Continuation<? super List<DocumentPoint>> $completion) {
    final String _sql = "SELECT * FROM document_points WHERE documentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DocumentPoint>>() {
      @Override
      @NonNull
      public List<DocumentPoint> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "documentId");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRadiusKm = CursorUtil.getColumnIndexOrThrow(_cursor, "radiusKm");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final List<DocumentPoint> _result = new ArrayList<DocumentPoint>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DocumentPoint _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDocumentId;
            _tmpDocumentId = _cursor.getString(_cursorIndexOfDocumentId);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final double _tmpRadiusKm;
            _tmpRadiusKm = _cursor.getDouble(_cursorIndexOfRadiusKm);
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            _item = new DocumentPoint(_tmpId,_tmpDocumentId,_tmpLatitude,_tmpLongitude,_tmpRadiusKm,_tmpLabel,_tmpAddress);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DocumentPoint>> getPointsForDocumentFlow(final String documentId) {
    final String _sql = "SELECT * FROM document_points WHERE documentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"document_points"}, new Callable<List<DocumentPoint>>() {
      @Override
      @NonNull
      public List<DocumentPoint> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "documentId");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRadiusKm = CursorUtil.getColumnIndexOrThrow(_cursor, "radiusKm");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final List<DocumentPoint> _result = new ArrayList<DocumentPoint>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DocumentPoint _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDocumentId;
            _tmpDocumentId = _cursor.getString(_cursorIndexOfDocumentId);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final double _tmpRadiusKm;
            _tmpRadiusKm = _cursor.getDouble(_cursorIndexOfRadiusKm);
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            _item = new DocumentPoint(_tmpId,_tmpDocumentId,_tmpLatitude,_tmpLongitude,_tmpRadiusKm,_tmpLabel,_tmpAddress);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<DocumentUse>> getUsesForDocument(final String documentId) {
    final String _sql = "SELECT * FROM document_uses WHERE documentId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"document_uses"}, new Callable<List<DocumentUse>>() {
      @Override
      @NonNull
      public List<DocumentUse> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "documentId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final List<DocumentUse> _result = new ArrayList<DocumentUse>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DocumentUse _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDocumentId;
            _tmpDocumentId = _cursor.getString(_cursorIndexOfDocumentId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            _item = new DocumentUse(_tmpId,_tmpDocumentId,_tmpTimestamp,_tmpLatitude,_tmpLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUseCount(final String documentId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM document_uses WHERE documentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
