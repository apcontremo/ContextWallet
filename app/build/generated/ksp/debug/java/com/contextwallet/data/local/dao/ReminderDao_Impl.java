package com.contextwallet.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.contextwallet.data.local.Converters;
import com.contextwallet.data.local.entity.ReminderType;
import com.contextwallet.data.local.entity.ScheduledReminder;
import java.lang.Class;
import java.lang.Exception;
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
public final class ReminderDao_Impl implements ReminderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ScheduledReminder> __insertionAdapterOfScheduledReminder;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ScheduledReminder> __deletionAdapterOfScheduledReminder;

  private final EntityDeletionOrUpdateAdapter<ScheduledReminder> __updateAdapterOfScheduledReminder;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRemindersForDocument;

  public ReminderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfScheduledReminder = new EntityInsertionAdapter<ScheduledReminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `scheduled_reminders` (`id`,`documentId`,`reminderType`,`scheduledTime`,`sent`,`workRequestId`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScheduledReminder entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDocumentId());
        final String _tmp = __converters.fromReminderType(entity.getReminderType());
        statement.bindString(3, _tmp);
        statement.bindLong(4, entity.getScheduledTime());
        final int _tmp_1 = entity.getSent() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        if (entity.getWorkRequestId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getWorkRequestId());
        }
      }
    };
    this.__deletionAdapterOfScheduledReminder = new EntityDeletionOrUpdateAdapter<ScheduledReminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `scheduled_reminders` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScheduledReminder entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfScheduledReminder = new EntityDeletionOrUpdateAdapter<ScheduledReminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `scheduled_reminders` SET `id` = ?,`documentId` = ?,`reminderType` = ?,`scheduledTime` = ?,`sent` = ?,`workRequestId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScheduledReminder entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDocumentId());
        final String _tmp = __converters.fromReminderType(entity.getReminderType());
        statement.bindString(3, _tmp);
        statement.bindLong(4, entity.getScheduledTime());
        final int _tmp_1 = entity.getSent() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        if (entity.getWorkRequestId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getWorkRequestId());
        }
        statement.bindString(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteRemindersForDocument = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM scheduled_reminders WHERE documentId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertReminder(final ScheduledReminder reminder,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfScheduledReminder.insertAndReturnId(reminder);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteReminder(final ScheduledReminder reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfScheduledReminder.handle(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReminder(final ScheduledReminder reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfScheduledReminder.handle(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRemindersForDocument(final String documentId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRemindersForDocument.acquire();
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
          __preparedStmtOfDeleteRemindersForDocument.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getRemindersForDocument(final String documentId,
      final Continuation<? super List<ScheduledReminder>> $completion) {
    final String _sql = "SELECT * FROM scheduled_reminders WHERE documentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ScheduledReminder>>() {
      @Override
      @NonNull
      public List<ScheduledReminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "documentId");
          final int _cursorIndexOfReminderType = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderType");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfSent = CursorUtil.getColumnIndexOrThrow(_cursor, "sent");
          final int _cursorIndexOfWorkRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "workRequestId");
          final List<ScheduledReminder> _result = new ArrayList<ScheduledReminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScheduledReminder _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDocumentId;
            _tmpDocumentId = _cursor.getString(_cursorIndexOfDocumentId);
            final ReminderType _tmpReminderType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfReminderType);
            _tmpReminderType = __converters.toReminderType(_tmp);
            final long _tmpScheduledTime;
            _tmpScheduledTime = _cursor.getLong(_cursorIndexOfScheduledTime);
            final boolean _tmpSent;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfSent);
            _tmpSent = _tmp_1 != 0;
            final String _tmpWorkRequestId;
            if (_cursor.isNull(_cursorIndexOfWorkRequestId)) {
              _tmpWorkRequestId = null;
            } else {
              _tmpWorkRequestId = _cursor.getString(_cursorIndexOfWorkRequestId);
            }
            _item = new ScheduledReminder(_tmpId,_tmpDocumentId,_tmpReminderType,_tmpScheduledTime,_tmpSent,_tmpWorkRequestId);
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
  public Flow<List<ScheduledReminder>> getRemindersForDocumentFlow(final String documentId) {
    final String _sql = "SELECT * FROM scheduled_reminders WHERE documentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"scheduled_reminders"}, new Callable<List<ScheduledReminder>>() {
      @Override
      @NonNull
      public List<ScheduledReminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "documentId");
          final int _cursorIndexOfReminderType = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderType");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfSent = CursorUtil.getColumnIndexOrThrow(_cursor, "sent");
          final int _cursorIndexOfWorkRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "workRequestId");
          final List<ScheduledReminder> _result = new ArrayList<ScheduledReminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScheduledReminder _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDocumentId;
            _tmpDocumentId = _cursor.getString(_cursorIndexOfDocumentId);
            final ReminderType _tmpReminderType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfReminderType);
            _tmpReminderType = __converters.toReminderType(_tmp);
            final long _tmpScheduledTime;
            _tmpScheduledTime = _cursor.getLong(_cursorIndexOfScheduledTime);
            final boolean _tmpSent;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfSent);
            _tmpSent = _tmp_1 != 0;
            final String _tmpWorkRequestId;
            if (_cursor.isNull(_cursorIndexOfWorkRequestId)) {
              _tmpWorkRequestId = null;
            } else {
              _tmpWorkRequestId = _cursor.getString(_cursorIndexOfWorkRequestId);
            }
            _item = new ScheduledReminder(_tmpId,_tmpDocumentId,_tmpReminderType,_tmpScheduledTime,_tmpSent,_tmpWorkRequestId);
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
  public Object getPendingReminders(final long currentTime,
      final Continuation<? super List<ScheduledReminder>> $completion) {
    final String _sql = "SELECT * FROM scheduled_reminders WHERE scheduledTime <= ? AND sent = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ScheduledReminder>>() {
      @Override
      @NonNull
      public List<ScheduledReminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "documentId");
          final int _cursorIndexOfReminderType = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderType");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfSent = CursorUtil.getColumnIndexOrThrow(_cursor, "sent");
          final int _cursorIndexOfWorkRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "workRequestId");
          final List<ScheduledReminder> _result = new ArrayList<ScheduledReminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScheduledReminder _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDocumentId;
            _tmpDocumentId = _cursor.getString(_cursorIndexOfDocumentId);
            final ReminderType _tmpReminderType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfReminderType);
            _tmpReminderType = __converters.toReminderType(_tmp);
            final long _tmpScheduledTime;
            _tmpScheduledTime = _cursor.getLong(_cursorIndexOfScheduledTime);
            final boolean _tmpSent;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfSent);
            _tmpSent = _tmp_1 != 0;
            final String _tmpWorkRequestId;
            if (_cursor.isNull(_cursorIndexOfWorkRequestId)) {
              _tmpWorkRequestId = null;
            } else {
              _tmpWorkRequestId = _cursor.getString(_cursorIndexOfWorkRequestId);
            }
            _item = new ScheduledReminder(_tmpId,_tmpDocumentId,_tmpReminderType,_tmpScheduledTime,_tmpSent,_tmpWorkRequestId);
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
  public Object getReminderByType(final String documentId, final ReminderType type,
      final Continuation<? super ScheduledReminder> $completion) {
    final String _sql = "SELECT * FROM scheduled_reminders WHERE documentId = ? AND reminderType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, documentId);
    _argIndex = 2;
    final String _tmp = __converters.fromReminderType(type);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ScheduledReminder>() {
      @Override
      @Nullable
      public ScheduledReminder call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "documentId");
          final int _cursorIndexOfReminderType = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderType");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfSent = CursorUtil.getColumnIndexOrThrow(_cursor, "sent");
          final int _cursorIndexOfWorkRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "workRequestId");
          final ScheduledReminder _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDocumentId;
            _tmpDocumentId = _cursor.getString(_cursorIndexOfDocumentId);
            final ReminderType _tmpReminderType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfReminderType);
            _tmpReminderType = __converters.toReminderType(_tmp_1);
            final long _tmpScheduledTime;
            _tmpScheduledTime = _cursor.getLong(_cursorIndexOfScheduledTime);
            final boolean _tmpSent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfSent);
            _tmpSent = _tmp_2 != 0;
            final String _tmpWorkRequestId;
            if (_cursor.isNull(_cursorIndexOfWorkRequestId)) {
              _tmpWorkRequestId = null;
            } else {
              _tmpWorkRequestId = _cursor.getString(_cursorIndexOfWorkRequestId);
            }
            _result = new ScheduledReminder(_tmpId,_tmpDocumentId,_tmpReminderType,_tmpScheduledTime,_tmpSent,_tmpWorkRequestId);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
