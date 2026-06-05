package com.contextwallet.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.contextwallet.data.local.dao.DocumentDao;
import com.contextwallet.data.local.dao.ReminderDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ReminderWorker_Factory {
  private final Provider<DocumentDao> documentDaoProvider;

  private final Provider<ReminderDao> reminderDaoProvider;

  public ReminderWorker_Factory(Provider<DocumentDao> documentDaoProvider,
      Provider<ReminderDao> reminderDaoProvider) {
    this.documentDaoProvider = documentDaoProvider;
    this.reminderDaoProvider = reminderDaoProvider;
  }

  public ReminderWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, documentDaoProvider.get(), reminderDaoProvider.get());
  }

  public static ReminderWorker_Factory create(
      javax.inject.Provider<DocumentDao> documentDaoProvider,
      javax.inject.Provider<ReminderDao> reminderDaoProvider) {
    return new ReminderWorker_Factory(Providers.asDaggerProvider(documentDaoProvider), Providers.asDaggerProvider(reminderDaoProvider));
  }

  public static ReminderWorker_Factory create(Provider<DocumentDao> documentDaoProvider,
      Provider<ReminderDao> reminderDaoProvider) {
    return new ReminderWorker_Factory(documentDaoProvider, reminderDaoProvider);
  }

  public static ReminderWorker newInstance(Context context, WorkerParameters workerParams,
      DocumentDao documentDao, ReminderDao reminderDao) {
    return new ReminderWorker(context, workerParams, documentDao, reminderDao);
  }
}
