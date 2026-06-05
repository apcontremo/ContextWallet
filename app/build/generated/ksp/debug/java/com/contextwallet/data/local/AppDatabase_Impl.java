package com.contextwallet.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.contextwallet.data.local.dao.DocumentDao;
import com.contextwallet.data.local.dao.DocumentDao_Impl;
import com.contextwallet.data.local.dao.ReminderDao;
import com.contextwallet.data.local.dao.ReminderDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile DocumentDao _documentDao;

  private volatile ReminderDao _reminderDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `documents` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER NOT NULL, `fileUri` TEXT NOT NULL, `fileType` TEXT NOT NULL, `defaultRadiusKm` REAL NOT NULL, `globalMode` INTEGER NOT NULL, `countryCode` TEXT, `continentCode` TEXT, `createdAt` INTEGER NOT NULL, `lastModified` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `document_points` (`id` TEXT NOT NULL, `documentId` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `radiusKm` REAL NOT NULL, `label` TEXT, `address` TEXT, PRIMARY KEY(`id`), FOREIGN KEY(`documentId`) REFERENCES `documents`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_document_points_documentId` ON `document_points` (`documentId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `document_uses` (`id` TEXT NOT NULL, `documentId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `latitude` REAL, `longitude` REAL, PRIMARY KEY(`id`), FOREIGN KEY(`documentId`) REFERENCES `documents`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_document_uses_documentId` ON `document_uses` (`documentId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `scheduled_reminders` (`id` TEXT NOT NULL, `documentId` TEXT NOT NULL, `reminderType` TEXT NOT NULL, `scheduledTime` INTEGER NOT NULL, `sent` INTEGER NOT NULL, `workRequestId` TEXT, PRIMARY KEY(`id`), FOREIGN KEY(`documentId`) REFERENCES `documents`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_scheduled_reminders_documentId` ON `scheduled_reminders` (`documentId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b6d39fec16d8f664f019a6fcbfd3e8c2')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `documents`");
        db.execSQL("DROP TABLE IF EXISTS `document_points`");
        db.execSQL("DROP TABLE IF EXISTS `document_uses`");
        db.execSQL("DROP TABLE IF EXISTS `scheduled_reminders`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsDocuments = new HashMap<String, TableInfo.Column>(12);
        _columnsDocuments.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("endDate", new TableInfo.Column("endDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("fileUri", new TableInfo.Column("fileUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("fileType", new TableInfo.Column("fileType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("defaultRadiusKm", new TableInfo.Column("defaultRadiusKm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("globalMode", new TableInfo.Column("globalMode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("countryCode", new TableInfo.Column("countryCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("continentCode", new TableInfo.Column("continentCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDocuments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDocuments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDocuments = new TableInfo("documents", _columnsDocuments, _foreignKeysDocuments, _indicesDocuments);
        final TableInfo _existingDocuments = TableInfo.read(db, "documents");
        if (!_infoDocuments.equals(_existingDocuments)) {
          return new RoomOpenHelper.ValidationResult(false, "documents(com.contextwallet.data.local.entity.Document).\n"
                  + " Expected:\n" + _infoDocuments + "\n"
                  + " Found:\n" + _existingDocuments);
        }
        final HashMap<String, TableInfo.Column> _columnsDocumentPoints = new HashMap<String, TableInfo.Column>(7);
        _columnsDocumentPoints.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentPoints.put("documentId", new TableInfo.Column("documentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentPoints.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentPoints.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentPoints.put("radiusKm", new TableInfo.Column("radiusKm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentPoints.put("label", new TableInfo.Column("label", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentPoints.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDocumentPoints = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDocumentPoints.add(new TableInfo.ForeignKey("documents", "CASCADE", "NO ACTION", Arrays.asList("documentId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesDocumentPoints = new HashSet<TableInfo.Index>(1);
        _indicesDocumentPoints.add(new TableInfo.Index("index_document_points_documentId", false, Arrays.asList("documentId"), Arrays.asList("ASC")));
        final TableInfo _infoDocumentPoints = new TableInfo("document_points", _columnsDocumentPoints, _foreignKeysDocumentPoints, _indicesDocumentPoints);
        final TableInfo _existingDocumentPoints = TableInfo.read(db, "document_points");
        if (!_infoDocumentPoints.equals(_existingDocumentPoints)) {
          return new RoomOpenHelper.ValidationResult(false, "document_points(com.contextwallet.data.local.entity.DocumentPoint).\n"
                  + " Expected:\n" + _infoDocumentPoints + "\n"
                  + " Found:\n" + _existingDocumentPoints);
        }
        final HashMap<String, TableInfo.Column> _columnsDocumentUses = new HashMap<String, TableInfo.Column>(5);
        _columnsDocumentUses.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentUses.put("documentId", new TableInfo.Column("documentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentUses.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentUses.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocumentUses.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDocumentUses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDocumentUses.add(new TableInfo.ForeignKey("documents", "CASCADE", "NO ACTION", Arrays.asList("documentId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesDocumentUses = new HashSet<TableInfo.Index>(1);
        _indicesDocumentUses.add(new TableInfo.Index("index_document_uses_documentId", false, Arrays.asList("documentId"), Arrays.asList("ASC")));
        final TableInfo _infoDocumentUses = new TableInfo("document_uses", _columnsDocumentUses, _foreignKeysDocumentUses, _indicesDocumentUses);
        final TableInfo _existingDocumentUses = TableInfo.read(db, "document_uses");
        if (!_infoDocumentUses.equals(_existingDocumentUses)) {
          return new RoomOpenHelper.ValidationResult(false, "document_uses(com.contextwallet.data.local.entity.DocumentUse).\n"
                  + " Expected:\n" + _infoDocumentUses + "\n"
                  + " Found:\n" + _existingDocumentUses);
        }
        final HashMap<String, TableInfo.Column> _columnsScheduledReminders = new HashMap<String, TableInfo.Column>(6);
        _columnsScheduledReminders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScheduledReminders.put("documentId", new TableInfo.Column("documentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScheduledReminders.put("reminderType", new TableInfo.Column("reminderType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScheduledReminders.put("scheduledTime", new TableInfo.Column("scheduledTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScheduledReminders.put("sent", new TableInfo.Column("sent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScheduledReminders.put("workRequestId", new TableInfo.Column("workRequestId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScheduledReminders = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysScheduledReminders.add(new TableInfo.ForeignKey("documents", "CASCADE", "NO ACTION", Arrays.asList("documentId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesScheduledReminders = new HashSet<TableInfo.Index>(1);
        _indicesScheduledReminders.add(new TableInfo.Index("index_scheduled_reminders_documentId", false, Arrays.asList("documentId"), Arrays.asList("ASC")));
        final TableInfo _infoScheduledReminders = new TableInfo("scheduled_reminders", _columnsScheduledReminders, _foreignKeysScheduledReminders, _indicesScheduledReminders);
        final TableInfo _existingScheduledReminders = TableInfo.read(db, "scheduled_reminders");
        if (!_infoScheduledReminders.equals(_existingScheduledReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "scheduled_reminders(com.contextwallet.data.local.entity.ScheduledReminder).\n"
                  + " Expected:\n" + _infoScheduledReminders + "\n"
                  + " Found:\n" + _existingScheduledReminders);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b6d39fec16d8f664f019a6fcbfd3e8c2", "083c6909da94c2808489bc0499831d3d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "documents","document_points","document_uses","scheduled_reminders");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `documents`");
      _db.execSQL("DELETE FROM `document_points`");
      _db.execSQL("DELETE FROM `document_uses`");
      _db.execSQL("DELETE FROM `scheduled_reminders`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(DocumentDao.class, DocumentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public DocumentDao documentDao() {
    if (_documentDao != null) {
      return _documentDao;
    } else {
      synchronized(this) {
        if(_documentDao == null) {
          _documentDao = new DocumentDao_Impl(this);
        }
        return _documentDao;
      }
    }
  }

  @Override
  public ReminderDao reminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }
}
