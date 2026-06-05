package com.contextwallet.data.repository;

import com.contextwallet.data.local.dao.DocumentDao;
import com.contextwallet.data.local.dao.ReminderDao;
import com.contextwallet.domain.usecase.DocumentStateCalculator;
import com.contextwallet.domain.usecase.ReminderScheduler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DocumentRepository_Factory implements Factory<DocumentRepository> {
  private final Provider<DocumentDao> documentDaoProvider;

  private final Provider<ReminderDao> reminderDaoProvider;

  private final Provider<DocumentStateCalculator> stateCalculatorProvider;

  private final Provider<ReminderScheduler> reminderSchedulerProvider;

  public DocumentRepository_Factory(Provider<DocumentDao> documentDaoProvider,
      Provider<ReminderDao> reminderDaoProvider,
      Provider<DocumentStateCalculator> stateCalculatorProvider,
      Provider<ReminderScheduler> reminderSchedulerProvider) {
    this.documentDaoProvider = documentDaoProvider;
    this.reminderDaoProvider = reminderDaoProvider;
    this.stateCalculatorProvider = stateCalculatorProvider;
    this.reminderSchedulerProvider = reminderSchedulerProvider;
  }

  @Override
  public DocumentRepository get() {
    return newInstance(documentDaoProvider.get(), reminderDaoProvider.get(), stateCalculatorProvider.get(), reminderSchedulerProvider.get());
  }

  public static DocumentRepository_Factory create(
      javax.inject.Provider<DocumentDao> documentDaoProvider,
      javax.inject.Provider<ReminderDao> reminderDaoProvider,
      javax.inject.Provider<DocumentStateCalculator> stateCalculatorProvider,
      javax.inject.Provider<ReminderScheduler> reminderSchedulerProvider) {
    return new DocumentRepository_Factory(Providers.asDaggerProvider(documentDaoProvider), Providers.asDaggerProvider(reminderDaoProvider), Providers.asDaggerProvider(stateCalculatorProvider), Providers.asDaggerProvider(reminderSchedulerProvider));
  }

  public static DocumentRepository_Factory create(Provider<DocumentDao> documentDaoProvider,
      Provider<ReminderDao> reminderDaoProvider,
      Provider<DocumentStateCalculator> stateCalculatorProvider,
      Provider<ReminderScheduler> reminderSchedulerProvider) {
    return new DocumentRepository_Factory(documentDaoProvider, reminderDaoProvider, stateCalculatorProvider, reminderSchedulerProvider);
  }

  public static DocumentRepository newInstance(DocumentDao documentDao, ReminderDao reminderDao,
      DocumentStateCalculator stateCalculator, ReminderScheduler reminderScheduler) {
    return new DocumentRepository(documentDao, reminderDao, stateCalculator, reminderScheduler);
  }
}
