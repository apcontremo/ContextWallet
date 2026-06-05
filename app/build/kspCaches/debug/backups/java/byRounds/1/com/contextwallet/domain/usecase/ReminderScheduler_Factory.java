package com.contextwallet.domain.usecase;

import androidx.work.WorkManager;
import com.contextwallet.data.local.dao.ReminderDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ReminderScheduler_Factory implements Factory<ReminderScheduler> {
  private final Provider<WorkManager> workManagerProvider;

  private final Provider<ReminderDao> reminderDaoProvider;

  public ReminderScheduler_Factory(Provider<WorkManager> workManagerProvider,
      Provider<ReminderDao> reminderDaoProvider) {
    this.workManagerProvider = workManagerProvider;
    this.reminderDaoProvider = reminderDaoProvider;
  }

  @Override
  public ReminderScheduler get() {
    return newInstance(workManagerProvider.get(), reminderDaoProvider.get());
  }

  public static ReminderScheduler_Factory create(
      javax.inject.Provider<WorkManager> workManagerProvider,
      javax.inject.Provider<ReminderDao> reminderDaoProvider) {
    return new ReminderScheduler_Factory(Providers.asDaggerProvider(workManagerProvider), Providers.asDaggerProvider(reminderDaoProvider));
  }

  public static ReminderScheduler_Factory create(Provider<WorkManager> workManagerProvider,
      Provider<ReminderDao> reminderDaoProvider) {
    return new ReminderScheduler_Factory(workManagerProvider, reminderDaoProvider);
  }

  public static ReminderScheduler newInstance(WorkManager workManager, ReminderDao reminderDao) {
    return new ReminderScheduler(workManager, reminderDao);
  }
}
